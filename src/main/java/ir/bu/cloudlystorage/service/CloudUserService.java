package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CloudUserService {
    Optional<CloudUser> findByToken(TokenDto tokenDto);

    String loginAndGetToken(UserDto userDto);

    void logout(String token);
}
