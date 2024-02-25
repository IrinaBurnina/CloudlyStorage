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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CloudUserServiceImpl implements CloudUserService {
    AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<CloudUser> findByToken(TokenDto tokenDto) {
        return usersRepository.getUserByToken(tokenDto.getAuthToken());
    }

    @Override
    public CloudUser getByLogin(String username) {
        return usersRepository.getUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username=[%s] not found", username)));
    }

    @Override
    public String loginAndGetToken(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.login(), userDto.password()
                )
        );
        CloudUser cloudUser = (CloudUser) authentication.getPrincipal();
        String token = jwtTokenProvider.generateAccessToken(authentication);
        cloudUser.setToken(token);
        usersRepository.save(cloudUser);
        return token;
    }

    @Override
    public void logout(String token) {
        Optional<CloudUser> cloudUserOptional = findByToken(new TokenDto(token));
        if (cloudUserOptional.isPresent()) {
            CloudUser cloudUser = CloudUser.builder().build();
            if (cloudUser.isEnabled() && cloudUser.isAccountNonExpired()
                    && cloudUser.isAccountNonLocked() && cloudUser.isCredentialsNonExpired()) {
                cloudUser.setToken(null);
                usersRepository.save(cloudUser);
            }
        } else throw new UserNotFoundException("User is not found.");
    }
}
