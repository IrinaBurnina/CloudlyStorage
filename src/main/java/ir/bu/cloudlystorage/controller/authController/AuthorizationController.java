package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AuthorizationController {
    TokenDto login(UserDto userDto);

    void logout(String token);
}
