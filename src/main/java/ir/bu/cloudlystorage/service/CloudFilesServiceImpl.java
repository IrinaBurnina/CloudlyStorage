package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.FileForResponseDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import ir.bu.cloudlystorage.exception.*;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.File;
import ir.bu.cloudlystorage.repository.FilesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class CloudFilesServiceImpl implements CloudFilesService {
    private final FilesRepository repository;
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public void uploadFile(MultipartFile file, String fileName) {
        try {
            repository.save(new File(
                            counter.getAndIncrement(),
                            fileName,
                            file.getSize(),
                            file.getBytes(),
                            getAuthenticatedUser(),
                            LocalDate.now()
                    )
            );
        } catch (IOException exception) {
            throw new ErrorInputData("Error input data of uploading file.");
        }
    }

    @Override
    public void deleteFile(String fileName) {
        File file = getFileByFileNameAndUser(fileName, getAuthenticatedUser());
        if (file == null) {
            throw new ErrorDeleteFile("Error of deleting file.");
        }
        repository.delete(file);
    }

    @Override
    public FileForResponseDownloadDto downLoadFile(String fileName) {
        return new FileForResponseDownloadDto(
                (MultipartFile) getFileByFileNameAndUser(fileName, getAuthenticatedUser())
        );
    }

    @Override
    public void editFileName(String fileNameOld, String fileName) {
        File file = getFileByFileNameAndUser(fileNameOld, getAuthenticatedUser());
        file.setFileName(fileName);
        repository.save(file);
    }

    @Override
    public List<FileForResponseGetDto> getAllFiles(Pageable ofSize) {
        List<File> files = repository.findByUser(getAuthenticatedUser(), (PageRequest) ofSize);
        List<FileForResponseGetDto> filesForResponse = new ArrayList<>(files.size());
        for (File file : files) {
            filesForResponse.add(new FileForResponseGetDto(file.getFileName(), (int) file.getSize()));
        }
        return filesForResponse;
    }

    private CloudUser getAuthenticatedUser() {
        CloudUser cloudUser = (CloudUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (cloudUser == null) {
            throw new UnauthorizedException("User is not authorized.");
        } else return cloudUser;
    }

    private File getFileByFileNameAndUser(String fileName, CloudUser cloudUser) {
        List<File> files = repository.findAllFilesByLogin(cloudUser.getLogin());
        if (files == null) {
            throw new ErrorUploadFile("Error uploading files.");
        }
        Optional<File> file = files
                .stream()
                .filter(c -> c.getFileName().equals(fileName))
                .findAny()
                .or(() -> {
                    throw new ErrorInputData("Error input data (file name).");
                });
        if (file.isPresent()) {
            return file.get();
        } else throw new FileNotFoundException("File is not found.");
    }
}
