package ir.bu.cloudlystorage.repository;

import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<CloudUser, List<File>> {
    //    void login(CloudUser cloudUser);
//    List<CloudUser> findAll();
//    List<File> getAllByUsername(String login);
    Optional<UserDto> getUserByLogin(String login);

    Optional<UserDto> getUserByToken(String token);
}
