package ir.bu.cloudlystorage.repository;

import ir.bu.cloudlystorage.model.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<CloudUser, String> {
    @Query(value = "select c from CloudUser c where c.login=:login")
    Optional<CloudUser> getUserByLogin(@Param("login") String login);

    @Query(value = "select c from CloudUser c where c.token=:authToken")
    Optional<CloudUser> getUserByToken(@Param("authToken") String authToken);
}
