package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;

import java.util.Optional;

public interface CloudUserService {
    Optional<CloudUser> findByToken(TokenDto tokenDto);

    String login(UserDto userDto);

    void logout(String token);
}
