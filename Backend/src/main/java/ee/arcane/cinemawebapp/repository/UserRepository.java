package ee.arcane.cinemawebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    boolean existsByUsernameIgnoreCase(@Param("username") String username);
}
