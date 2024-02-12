package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.controller.authController.AuthorizationController;
import ir.bu.cloudlystorage.controller.authController.AuthorizationControllerImpl;
import ir.bu.cloudlystorage.dto.FileForRequestDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import ir.bu.cloudlystorage.service.CloudFilesService;
import ir.bu.cloudlystorage.service.CloudUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ControllerTest {
    CloudUserService userService = Mockito.mock(CloudUserService.class);
    CloudFilesService filesService = Mockito.mock(CloudFilesService.class);
    CloudController controller = new CloudControllerImpl(filesService);

    @Test
    public void uploadFileTest() throws IOException {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        MultipartFile file = new MockMultipartFile(fileName, new byte[1]);
        //act
        controller.uploadFile(fileName, file);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).uploadFile(file, fileName);
    }

    @Test
    public void deleteFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestDto fileForRequestDto = new FileForRequestDto(fileName);
        //act
        controller.deleteFile(fileName);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).deleteFile(fileName);
    }

    @Test
    public void downLoadFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestDto fileForRequestDto = new FileForRequestDto(fileName);
        //act
        controller.downLoadFile(fileName);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).downLoadFile(fileName);
    }

    @Test
    public void editFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        String newFileName = "testNew.txt";
        FileForRequestDto fileForRequestDto = new FileForRequestDto(fileName);
        //act
        controller.editFileName(fileName, fileForRequestDto);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).editFileName(fileName, newFileName);
    }

    @Test
    public void getAllFilesTest() {
        //arrange
        List<FileForResponseGetDto> filesExpected = Arrays.asList(
                new FileForResponseGetDto("test_1.jpg", 1000),
                new FileForResponseGetDto("test_2.jpg", 1001)
        );
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(filesService.getAllFiles(PageRequest.of(0, 2))).thenReturn(filesExpected);
        CloudController controller = new CloudControllerImpl(filesService);
        AuthorizationController authController = new AuthorizationControllerImpl(userService);
        //act
        List<FileForResponseGetDto> filesActual = controller.getAllFiles(2);
        //assert
        Assertions.assertEquals(filesExpected, filesActual);
    }

}
