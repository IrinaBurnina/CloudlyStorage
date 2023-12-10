package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.model.CloudUser;

public interface CloudController {
    TokenDto login(CloudUser cloudUser);
//    void logout(CloudUser cloudUser);
//    void uploadFile(File file, CloudUser cloudUser);
//    void deleteFile(File file);
//    void downLoadFile();
//    void editFileName(File file);

//    @DeleteMapping("/file")
//    void deleteFile(File file, CloudUser cloudUser);
//
//    @GetMapping
//    void downLoadFile(CloudUser cloudUser);
//
//    @PutMapping("/file")
//    void editFileName(File file, CloudUser cloudUser);
//
//    void getAllFiles();
//
//    @GetMapping("/list")
//    void getAllFiles(CloudUser cloudUser);
}
