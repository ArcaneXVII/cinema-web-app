package ee.arcane.cinemawebapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReserveDto {
    @NotNull
    private Integer screeningId;
    @NotNull
    private Integer seatRow;
    @NotNull
    private Integer seatNumber;
}
