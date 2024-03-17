package ee.arcane.cinemawebapp.repository;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Setter(AccessLevel.PROTECTED)
    @Column(name = "user_id")
    private Integer userID;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}
