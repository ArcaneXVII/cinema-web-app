package ee.arcane.cinemawebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByScreeningId(Integer screeningId);
}
