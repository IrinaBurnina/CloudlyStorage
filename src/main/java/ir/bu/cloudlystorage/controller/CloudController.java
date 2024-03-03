package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.FileForRequestDto;
import ir.bu.cloudlystorage.dto.FileForResponseDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public interface CloudController {
    ResponseEntity<Void> uploadFile(String fileName, MultipartFile file) throws IOException;

    ResponseEntity<Void> deleteFile(String fileName);

    ResponseEntity<FileForResponseDownloadDto> downLoadFile(String fileName);

    ResponseEntity<Void> editFileName(String fileName, FileForRequestDto fileForRequestDto);


    List<FileForResponseGetDto> getAllFiles(int limit);
}
