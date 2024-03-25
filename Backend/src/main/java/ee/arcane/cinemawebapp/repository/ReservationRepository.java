package ee.arcane.cinemawebapp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByScreeningId(Integer screeningId);
    boolean existsByScreeningIdAndSeatRowAndSeatNumber(Integer screeningId, Integer seatRow, Integer seatNumber);
    boolean existsByScreeningIdAndUserId(Integer screeningId, Integer userId);
    @Transactional
    void deleteByScreeningIdAndUserId(Integer screeningId, Integer userId);
}
