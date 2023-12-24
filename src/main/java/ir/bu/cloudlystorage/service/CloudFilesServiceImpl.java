package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.FileForRequestQueryDto;
import ir.bu.cloudlystorage.dto.FileForResponseDeleteDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import ir.bu.cloudlystorage.exception.*;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.File;
import ir.bu.cloudlystorage.repository.FilesRepository;
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

@Service
public class CloudFilesServiceImpl implements CloudFilesService {
    private final FilesRepository repository;

    public CloudFilesServiceImpl(/*@Qualifier (value = "files")*/ FilesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void uploadFile(MultipartFile file, FileForRequestQueryDto fileForRequestQueryDto) {
        try {
            repository.save(new File(0, fileForRequestQueryDto.getFileName(), file.getSize(),
                    file.getBytes(), getAuthenticatedUser(), LocalDate.now()));
        } catch (IOException exception) {
            throw new ErrorInputData("Error input data of uploading file.");
        }

    }

    @Override
    public FileForResponseDeleteDownloadDto deleteFile(FileForRequestQueryDto fileForRequestQueryDto) {
        File file = getFileByFileNameAndUser(fileForRequestQueryDto.getFileName(), getAuthenticatedUser());
        if (file == null) {
            throw new ErrorDeleteFile("Error of deleting file.");
        }
        repository.delete(file);
        return new FileForResponseDeleteDownloadDto((MultipartFile) file);
    }

    @Override
    public FileForResponseDeleteDownloadDto downLoadFile(FileForRequestQueryDto fileForRequestQueryDto) {
//        new ByteArrayResource(
//                getFileByFileNameAndUser(
//                        fileForRequestQueryDto.getFileName(),
//                        getAuthenticatedUser()).getContent()
//        );
        return new FileForResponseDeleteDownloadDto(
                (MultipartFile) getFileByFileNameAndUser(
                        fileForRequestQueryDto.getFileName(),
                        getAuthenticatedUser()
                )
        );
    }

    @Override
    public void editFileName(String fileName, FileForRequestQueryDto fileForRequestQueryDto) {
        File file = getFileByFileNameAndUser(fileForRequestQueryDto.getFileName(), getAuthenticatedUser());
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
