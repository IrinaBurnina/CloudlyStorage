package ir.bu.cloudlystorage.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileForResponseDownloadDto(MultipartFile file) {
}
