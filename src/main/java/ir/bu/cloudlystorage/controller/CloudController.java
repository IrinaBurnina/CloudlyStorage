package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.FileForRequestQueryDto;
import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public interface CloudController {
    TokenDto login(UserDto userDto);

    void logout(String token);

    void uploadFile(String fileName, MultipartFile file) throws IOException;

    void deleteFile(String fileName);

    void downLoadFile(String fileName);

    void editFileName(String fileName, FileForRequestQueryDto fileForRequestQueryDto);

    @GetMapping("/list")
    void getAllFiles(@RequestParam("limit") int limit);

    // void getAllFiles(int limit);
}
