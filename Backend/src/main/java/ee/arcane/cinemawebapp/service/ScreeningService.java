package ee.arcane.cinemawebapp.service;

import ee.arcane.cinemawebapp.dto.ReservationDto;
import ee.arcane.cinemawebapp.dto.ReserveDto;
import ee.arcane.cinemawebapp.dto.SeatRecommendationDto;
import ee.arcane.cinemawebapp.repository.Reservation;
import ee.arcane.cinemawebapp.repository.ReservationRepository;
import ee.arcane.cinemawebapp.repository.Screening;
import ee.arcane.cinemawebapp.repository.ScreeningRepository;
import ee.arcane.cinemawebapp.utility.SeatReservationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;
    private Random random = new Random();

    public ResponseEntity<List<Screening>> findActiveScreenings() {
        Specification<Screening> spec = (root, query, cb) -> cb.greaterThan(root.get("dateStart"), ZonedDateTime.now());
        Sort sort = Sort.by(Sort.Direction.ASC, "dateStart");
        return ResponseEntity.ok(screeningRepository.findAll(spec, sort));
    }

    public ResponseEntity<List<ReservationDto>> findScreeningReservations(Integer screeningId) {
        List<Reservation> reservations = reservationRepository.findAllByScreeningId(screeningId);
        // Convert objects to DTOs. Could be done with a mapper instead.
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setScreeningId(reservation.getScreeningId());
            reservationDto.setSeatRow(reservation.getSeatRow());
            reservationDto.setSeatNumber(reservation.getSeatNumber());
            reservationDtos.add(reservationDto);
        }
        return ResponseEntity.ok(reservationDtos);
    }

    public ResponseEntity<SeatRecommendationDto> findScreeningSeatRecommendation(Integer screeningId, Integer seatAmount) {
        List<Reservation> reservations = reservationRepository.findAllByScreeningId(screeningId);
        SeatReservationUtility seatReservationUtility = new SeatReservationUtility();
        for (Reservation reservation : reservations) {
            seatReservationUtility.setSeatReserved(reservation.getSeatRow(), reservation.getSeatNumber());
        }
        List<List<Integer>> recommendedSeats = seatReservationUtility.getSeatRecommendation(seatAmount);
        SeatRecommendationDto seatRecommendationDto = new SeatRecommendationDto();
        seatRecommendationDto.setRecommendedSeats(recommendedSeats);

        return ResponseEntity.ok(seatRecommendationDto);
    }

    public ResponseEntity<String> reserveScreeningSeat(ReserveDto data) {
        if (!screeningRepository.existsByScreeningId(data.getScreeningId())) {
            return ResponseEntity.badRequest().body("Screening does not exist");
        }
        if (reservationRepository.existsByScreeningIdAndSeatRowAndSeatNumber(data.getScreeningId(), data.getSeatRow(), data.getSeatNumber())) {
            return ResponseEntity.badRequest().body("Seat is already reserved");
        }
        if (data.getSeatNumber() == null || data.getSeatRow() == null) {
            return ResponseEntity.badRequest().body("Seat number or row not provided");
        }
        Reservation reservation = new Reservation();
        Integer userId = Integer.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        reservation.setUserId(userId);
        reservation.setScreeningId(data.getScreeningId());
        reservation.setSeatNumber(data.getSeatNumber());
        reservation.setSeatRow(data.getSeatRow());
        reservationRepository.save(reservation);
        return ResponseEntity.ok("Reservation successful");
    }

    public void populateScreeningReservations(Integer screeningId) {
        for (int row = 2; row < 10; row++) {
            for (int amount = 0; amount < random.nextInt(9) + 4; amount++) {
                int seat = random.nextInt(14) + 2;
                if (reservationRepository.existsByScreeningIdAndSeatRowAndSeatNumber(screeningId, row, seat + 1)) {
                    continue;
                }
                Reservation reservation = new Reservation();
                reservation.setScreeningId(screeningId);
                reservation.setSeatRow(row);
                reservation.setSeatNumber(seat);
                reservationRepository.save(reservation);
            }
        }
    }

    public ResponseEntity<String> cancelScreeningReservation(Integer screeningId) {
        Integer userId = Integer.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!reservationRepository.existsByScreeningIdAndUserId(screeningId, userId)) {
            return ResponseEntity.badRequest().body("No reservations for this screening found.");
        }
        reservationRepository.deleteByScreeningIdAndUserId(screeningId, userId);
        return ResponseEntity.ok("Reservation cancelled");
    }
}
