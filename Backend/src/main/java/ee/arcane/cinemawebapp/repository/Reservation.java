package ee.arcane.cinemawebapp.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "reservations")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", referencedColumnName = "screening_id",insertable = false, updatable = false)
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",insertable = false, updatable = false)
    private User user;

    @Min(value = 1, message = "Seat row must be between 1 and 10")
    @Max(value = 10, message = "Seat row must be between 1 and 10")
    @Column(name = "seat_row")
    private Integer seatRow;

    @Min(value = 1, message = "Seat number must be between 1 and 18")
    @Max(value = 18, message = "Seat number must be between 1 and 18")
    @Column(name = "seat_number")
    private Integer seatNumber;
}
