package ee.arcane.cinemawebapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SeatRecommendationDto {
    List<List<Integer>> recommendedSeats;
}
