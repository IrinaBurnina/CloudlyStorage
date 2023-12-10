package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.service.FileServiceImpl;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud")
@Validated
public class CloudControllerImpl implements CloudController {

    private final FileServiceImpl service;

    public CloudControllerImpl(FileServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/hi")
    public String hello() {
        return "Hello, please login to use our service";
    }

    @PostMapping("/login")
    @Override
    public TokenDto login(@Valid CloudUser cloudUser) {
        service.login(cloudUser);
        return new TokenDto();
    }

//    @PostMapping("/logout")
//    @Override
//    public void logout(CloudUser cloudUser) {
//        service.logout(cloudUser);
//    }
//
//    @PostMapping(value = "/file")
//    @Override
//    public void uploadFile(File file, CloudUser cloudUser) {
//        service.uploadFile(file, cloudUser);
//    }
//
//    @DeleteMapping("/file")
//    @Override
//    public void deleteFile(File file, CloudUser cloudUser) {
//        service.deleteFile(file);
//    }
//
//    @GetMapping
//    @Override
//    public void downLoadFile(CloudUser cloudUser) {
//        service.downLoadFile();
//    }
//
//    @PutMapping("/file")
//    @Override
//    public void editFileName(File file, CloudUser cloudUser) {
//        service.editFileName(file);
//    }
//
//    @GetMapping("/list")
//    @Override
//    public void getAllFiles(CloudUser cloudUser) {
//        service.getAllFiles();
//    }
}
