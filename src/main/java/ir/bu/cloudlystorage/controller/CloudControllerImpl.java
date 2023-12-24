package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.FileForRequestQueryDto;
import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.service.CloudFilesService;
import ir.bu.cloudlystorage.service.CloudUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cloud")
@CrossOrigin(origins = "http://localhost:8081")
public class CloudControllerImpl implements CloudController {

    private final CloudFilesService filesService;
    private final CloudUserService usersService;

    public CloudControllerImpl(CloudFilesService filesService, CloudUserService usersService) {
        this.filesService = filesService;
        this.usersService = usersService;
    }

    @GetMapping("/hi")
    public String hello() {
        return "Hello, please login to use our service";
    }

    @PostMapping("/login")
    @Override
    public TokenDto login(@RequestBody UserDto userDto) {
        String authToken = usersService.loginAndGetToken(userDto);
        return new TokenDto(authToken);
    }

    @PostMapping("/logout")
    @Override
    public void logout(@RequestHeader(name = "auth-token") String token) {
        usersService.logout(token);
    }

    @PostMapping("/file")
    @Override
    public void uploadFile(@RequestParam("filename") String fileName, @RequestParam("file") MultipartFile file) throws IOException {
        filesService.uploadFile(file, new FileForRequestQueryDto(fileName));
    }

    @DeleteMapping("/file")
    @Override
    public void deleteFile(@RequestParam("filename") String fileName) {
        filesService.deleteFile(new FileForRequestQueryDto(fileName));
    }
//    @DeleteMapping("/file")
//    @Override
//    public ResponseEntity<?> deleteFile(@RequestParam("filename") String fileName) {
//
//        fileRepository.deleteByFilename(fileName);
//
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/file")
    @Override
    public void downLoadFile(@RequestParam("filename") String fileName) {
        filesService.downLoadFile(new FileForRequestQueryDto(fileName));
    }

    @PutMapping("/file")
    @Override
    public void editFileName(@RequestParam("filename") String fileName, @RequestBody FileForRequestQueryDto fileForRequestQueryDto) {
        filesService.editFileName(fileName, fileForRequestQueryDto);
    }

    @GetMapping("/list")
    @Override
    public void getAllFiles(@RequestParam("limit") int limit) {
        filesService.getAllFiles(Pageable.ofSize(limit));
    }
}
