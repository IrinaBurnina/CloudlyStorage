package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CloudUserServiceImpl implements CloudUserService {
    private final UsersRepository usersRepository;

    public CloudUserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<CloudUser> findByToken(TokenDto tokenDto) {
        return Optional.empty();
    }

    @Override
    public String login(UserDto userDto) {
        return null;
    }

    @Override
    public void logout(String token) {

    }
}
