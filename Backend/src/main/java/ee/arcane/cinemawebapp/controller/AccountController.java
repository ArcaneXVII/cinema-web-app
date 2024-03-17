package ee.arcane.cinemawebapp.controller;

import ee.arcane.cinemawebapp.dto.LoginDto;
import ee.arcane.cinemawebapp.dto.RegistrationDto;
import ee.arcane.cinemawebapp.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping(path = "/account")
    public ResponseEntity<String> registerAccount(@Valid @RequestBody RegistrationDto data) {
        return accountService.registerAccount(data);
    }

    @PutMapping(path = "/account")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto data) {
        return accountService.login(data);
    }

}
