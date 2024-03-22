package ee.arcane.cinemawebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByScreeningId(Integer screeningId);
    boolean existsByScreeningId(Integer screeningId);
    boolean existsByScreeningIdAndSeatRowAndSeatNumber(Integer screeningId, Integer seatRow, Integer seatNumber);
}
