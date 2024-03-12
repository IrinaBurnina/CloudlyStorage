package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;

public interface AuthService {
    TokenDto loginAndGetToken(UserDto userDto);

    void logout(String authToken);
}
