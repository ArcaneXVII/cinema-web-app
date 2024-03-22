package ee.arcane.cinemawebapp.service;

import ee.arcane.cinemawebapp.dto.ReserveDto;
import ee.arcane.cinemawebapp.repository.Reservation;
import ee.arcane.cinemawebapp.repository.ReservationRepository;
import ee.arcane.cinemawebapp.repository.Screening;
import ee.arcane.cinemawebapp.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;

    public ResponseEntity<List<Screening>> findActiveScreenings() {
        return ResponseEntity.ok(screeningRepository.findAllByDateStartAfter(ZonedDateTime.now()));
    }

    public ResponseEntity<List<Reservation>> findScreeningReservations(Integer screeningId) {
        return ResponseEntity.ok(reservationRepository.findAllByScreeningId(screeningId));
    }

    public ResponseEntity<String> reserveScreeningSeat(ReserveDto data) {
        if (!reservationRepository.existsByScreeningId(data.getScreeningId())) {
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
}
