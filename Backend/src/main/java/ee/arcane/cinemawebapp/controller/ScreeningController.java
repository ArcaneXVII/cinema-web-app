package ee.arcane.cinemawebapp.controller;


import ee.arcane.cinemawebapp.dto.ReserveDto;
import ee.arcane.cinemawebapp.repository.Reservation;
import ee.arcane.cinemawebapp.repository.Screening;
import ee.arcane.cinemawebapp.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ScreeningController {
    private final ScreeningService screeningService;

    @GetMapping(path = "/screening/active")
    public ResponseEntity<List<Screening>> findActiveScreenings() {
        return screeningService.findActiveScreenings();
    }

    @GetMapping(path = "/screening/reservations")
    public ResponseEntity<List<Reservation>> findScreeningReservations(@RequestParam("id") Integer screeningId) {
        return screeningService.findScreeningReservations(screeningId);
    }

    @PostMapping(path = "/screening/reservation")
    public ResponseEntity<String> reserveScreeningSeat(@RequestBody ReserveDto data) {
        return screeningService.reserveScreeningSeat(data);
    }
}
