package ir.bu.cloudlystorage.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileForResponseDeleteDownloadDto {
    private MultipartFile file;

    public FileForResponseDeleteDownloadDto(MultipartFile file) {
        this.file = file;
    }
}
