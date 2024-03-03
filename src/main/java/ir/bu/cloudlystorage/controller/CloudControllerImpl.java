package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.FileForRequestDto;
import ir.bu.cloudlystorage.dto.FileForResponseDownloadDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import ir.bu.cloudlystorage.service.CloudFilesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cloud")
@CrossOrigin(origins = {"${settings.cross_origin}"})
@AllArgsConstructor
public class CloudControllerImpl implements CloudController {

    private final CloudFilesService filesService;

    @PostMapping("/file")
    @Override
    public ResponseEntity<Void> uploadFile(@RequestParam("filename") String fileName, @RequestParam("file") MultipartFile file) {
        filesService.uploadFile(file, fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    @Override
    public ResponseEntity<Void> deleteFile(@RequestParam("filename") String fileName) {
        filesService.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/file")
    @Override
    public ResponseEntity<FileForResponseDownloadDto> downLoadFile(@RequestParam("filename") String fileName) {
        return new ResponseEntity<>(filesService.downLoadFile(fileName), HttpStatus.OK);
    }

    @PutMapping("/file")
    @Override
    public ResponseEntity<Void> editFileName(@RequestParam("filename") String fileName,
                                             @RequestBody FileForRequestDto fileForRequestDto) {
        filesService.editFileName(fileName, fileForRequestDto.fileName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    @Override
    public List<FileForResponseGetDto> getAllFiles(@RequestParam("limit") int limit) {
        return filesService.getAllFiles(Pageable.ofSize(limit));
    }
}
