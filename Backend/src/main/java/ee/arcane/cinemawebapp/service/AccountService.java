package ee.arcane.cinemawebapp.service;


import ee.arcane.cinemawebapp.dto.LoginDto;
import ee.arcane.cinemawebapp.dto.RegistrationDto;
import ee.arcane.cinemawebapp.repository.User;
import ee.arcane.cinemawebapp.repository.UserRepository;
import ee.arcane.cinemawebapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerAccount(RegistrationDto data) {
        if (userRepository.existsByEmail(data.getEmail()) || userRepository.existsByUsernameIgnoreCase(data.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Name or email already taken");
        }

        User newUser = new User();
        newUser.setUsername(data.getUsername());
        newUser.setEmail(data.getEmail());
        newUser.setPassword(passwordEncoder.encode(data.getPassword()));
        userRepository.save(newUser);

        return ResponseEntity.ok("Account successfully created");
    }

    public ResponseEntity<String> login(LoginDto data) {
        User user = userRepository.findByEmail(data.getEmail());
        if (user != null && passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            String jwt = JwtTokenProvider.generateToken(user.getUserID().toString());
            return ResponseEntity.ok().body(jwt);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
