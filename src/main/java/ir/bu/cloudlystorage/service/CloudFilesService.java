package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.FileForResponseDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CloudFilesService {

    void uploadFile(MultipartFile file, String fileName);

    void deleteFile(String fileName);

    FileForResponseDownloadDto downLoadFile(String fileName);

    void editFileName(String fileNameOld, String fileName);

    List<FileForResponseGetDto> getAllFiles(Pageable ofSize);

}
