package ee.arcane.cinemawebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    List<Screening> findAllByDateStartAfter(ZonedDateTime dateStart);
    Screening findByScreeningID(Integer screeningID);

}
