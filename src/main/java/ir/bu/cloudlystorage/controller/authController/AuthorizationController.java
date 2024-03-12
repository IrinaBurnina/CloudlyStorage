package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AuthorizationController {
    ResponseEntity<TokenDto> login(UserDto userDto);

    ResponseEntity<Void> logout(String token);
}
