package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.FileForRequestQueryDto;
import ir.bu.cloudlystorage.dto.FileForResponseDeleteDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CloudFilesService {

    void uploadFile(MultipartFile file, FileForRequestQueryDto fileForRequestQueryDto) throws IOException;

    FileForResponseDeleteDownloadDto deleteFile(FileForRequestQueryDto fileForRequestQueryDto);

    FileForResponseDeleteDownloadDto downLoadFile(FileForRequestQueryDto fileForRequestQueryDto);

    void editFileName(String fileName, FileForRequestQueryDto fileForRequestQueryDto);

    List<FileForResponseGetDto> getAllFiles(Pageable ofSize);

}
