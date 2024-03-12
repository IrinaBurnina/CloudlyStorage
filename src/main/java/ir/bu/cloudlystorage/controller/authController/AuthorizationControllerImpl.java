package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthorizationControllerImpl implements AuthorizationController {
    private final AuthService authService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        final var response = authService.loginAndGetToken(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Override
    public ResponseEntity<Void> logout(@RequestHeader(name = "auth-token") String token) {
        authService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
