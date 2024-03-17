package ee.arcane.cinemawebapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
