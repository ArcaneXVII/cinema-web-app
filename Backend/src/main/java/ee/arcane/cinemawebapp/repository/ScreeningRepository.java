package ee.arcane.cinemawebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScreeningRepository extends JpaRepository<Screening, Integer>, JpaSpecificationExecutor<Screening> {

    Screening findByScreeningId(Integer screeningId);
    boolean existsByScreeningId(Integer screeningId);
}
