package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CloudUserService {
    Optional<CloudUser> findByToken(TokenDto tokenDto);

    String loginAndGetToken(UserDto userDto);

    void logout(String token);
}
