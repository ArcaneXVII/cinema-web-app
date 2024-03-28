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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;

    public ResponseEntity<List<Screening>> findActiveScreenings() {
        return ResponseEntity.ok(screeningRepository.findAllByDateStartAfter(ZonedDateTime.now()));
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
        Reservation reservation = new Reservation();
        Integer userId = Integer.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        reservation.setUserId(userId);
        reservation.setScreeningId(data.getScreeningId());
        reservation.setSeatNumber(data.getSeatNumber());
        reservation.setSeatRow(data.getSeatRow());
        reservationRepository.save(reservation);
        return ResponseEntity.ok("Reservation successful");
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
