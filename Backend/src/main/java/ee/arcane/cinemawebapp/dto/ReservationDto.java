package ee.arcane.cinemawebapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDto {
    private Integer screeningId;
    private Integer seatRow;
    private Integer seatNumber;
}
