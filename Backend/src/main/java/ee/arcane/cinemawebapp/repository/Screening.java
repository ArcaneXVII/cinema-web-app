package ee.arcane.cinemawebapp.repository;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Table(name = "screenings")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screening {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Setter(AccessLevel.PROTECTED)
    @Column(name = "screening_id")
    private Integer screeningId;

    private String movie;

    private String genre;

    private String language;

    // Based on MPA film rating system
    @Column(name = "film_rating")
    private String filmRating;

    @Column(name = "date_start")
    private ZonedDateTime dateStart;

    @Column(name = "date_end")
    private ZonedDateTime dateEnd;
}
