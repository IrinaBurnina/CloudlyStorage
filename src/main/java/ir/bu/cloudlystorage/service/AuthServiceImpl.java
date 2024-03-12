package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.exception.UserNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import ir.bu.cloudlystorage.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    UsersRepository repository;

    @Override
    public TokenDto loginAndGetToken(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.login(), userDto.password()
                )
        );
        CloudUser cloudUser = (CloudUser) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateAccessToken(authentication);
        cloudUser.setToken(token);
        repository.save(cloudUser);
        return new TokenDto(token);
    }

    @Override
    public void logout(String token) {
        Optional<CloudUser> cloudUserOptional = repository.getUserByToken(token
                .split(" ")[1]);
        if (cloudUserOptional.isPresent()) {
            cloudUserOptional.get().setToken(null);
            repository.save(cloudUserOptional.get());
        } else throw new UserNotFoundException("User not found by token");
    }
}
