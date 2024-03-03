package ir.bu.cloudlystorage.repository;

import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.File;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<File, Long> {
    @Query(value = "select f from File f where f.user.login=:login and f.fileName=:filename")
    Optional<File> findByFileNameAndLogin(@Param("filename") String fileName, @Param("login") String login);

    @Query(value = "select f from File f where f.user.login=:login")
    List<File> findAllFilesByLogin(@Param("login") String login);

    List<File> findByUser(CloudUser cloudUser, PageRequest pageRequest);
}
