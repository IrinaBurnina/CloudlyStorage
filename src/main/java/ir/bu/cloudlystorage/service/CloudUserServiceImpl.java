package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.exception.UserNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CloudUserServiceImpl implements CloudUserService {
    AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private TokenDto token;

    public CloudUserServiceImpl(UsersRepository usersRepository, AuthenticationManager authenticationManager, TokenDto token) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.token = token;
    }

    @Override
    public Optional<CloudUser> findByToken(TokenDto tokenDto) {
        System.out.println("findByToken" + usersRepository.getUserByToken(tokenDto.getAuthToken()));
        return usersRepository.getUserByToken(tokenDto.getAuthToken());
    }

    @Override
    public String loginAndGetToken(UserDto userDto) {
        System.out.println("1 - loginAndGetToken");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.login(), userDto.password()
                )
        );
        CloudUser cloudUser = (CloudUser) authentication.getPrincipal();
        cloudUser.setToken(token.generate());
        System.out.println(token);
        usersRepository.save(cloudUser);
        return token.getAuthToken();
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
