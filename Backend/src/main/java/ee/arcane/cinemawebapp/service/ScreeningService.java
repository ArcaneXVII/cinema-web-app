package ee.arcane.cinemawebapp.service;

import ee.arcane.cinemawebapp.repository.Reservation;
import ee.arcane.cinemawebapp.repository.ReservationRepository;
import ee.arcane.cinemawebapp.repository.Screening;
import ee.arcane.cinemawebapp.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
