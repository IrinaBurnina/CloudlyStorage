package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CloudUserServiceImpl implements CloudUserService {
    private final UsersRepository usersRepository;

    @Override
    public Optional<CloudUser> findByToken(TokenDto tokenDto) {
        return usersRepository.getUserByToken(tokenDto.getAuthToken());
    }

    @Override
    public CloudUser getByLogin(String username) {
        return usersRepository.getUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username=[%s] not found", username)));
    }
}
