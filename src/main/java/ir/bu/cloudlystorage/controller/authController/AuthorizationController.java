package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;

public interface AuthorizationController {
    TokenDto login(UserDto userDto);//TODO зачем мне тут этот метод?

    void logout(String token);
}
