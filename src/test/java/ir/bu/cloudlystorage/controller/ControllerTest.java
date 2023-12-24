package ir.bu.cloudlystorage.controller;

import ir.bu.cloudlystorage.dto.FileForRequestQueryDto;
import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.dto.UserDto;
import ir.bu.cloudlystorage.service.CloudFilesService;
import ir.bu.cloudlystorage.service.CloudUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ControllerTest {
    CloudUserService userService = Mockito.mock(CloudUserService.class);
    CloudFilesService filesService = Mockito.mock(CloudFilesService.class);
    CloudController controller = new CloudControllerImpl(filesService, userService);
    String token = "00002222";

    @Test
    public void loginTest() {
        //arrange
        UserDto userDtoTest = new UserDto("kot", "kot");
        TokenDto tokenDtoExpected = new TokenDto("00002222");
        Mockito.when(userService.loginAndGetToken(userDtoTest)).thenReturn(token);
        //act
        TokenDto tokenDto = controller.login(userDtoTest);
        //assert
        Assertions.assertEquals(tokenDtoExpected, tokenDto);
    }

    @Test
    public void logoutTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        //act
        controller.logout(token);
        // assert
        Mockito.verify(userService, Mockito.times(wantedNumberInvocationInt)).logout(token);
    }

    @Test
    public void uploadFileTest() throws IOException {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestQueryDto fileForRequestQueryDto = new FileForRequestQueryDto(fileName);
        MultipartFile file = new MockMultipartFile(fileName, new byte[1]);
        //act
        controller.uploadFile(fileName, file);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).uploadFile(file, fileForRequestQueryDto);
    }

    @Test
    public void deleteFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestQueryDto fileForRequestQueryDto = new FileForRequestQueryDto(fileName);
        //act
        controller.deleteFile(fileName);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).deleteFile(fileForRequestQueryDto);
    }

    @Test
    public void downLoadFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestQueryDto fileForRequestQueryDto = new FileForRequestQueryDto(fileName);
        //act
        controller.downLoadFile(fileName);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).downLoadFile(fileForRequestQueryDto);
    }

    @Test
    public void editFileTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String fileName = "test.txt";
        FileForRequestQueryDto fileForRequestQueryDto = new FileForRequestQueryDto(fileName);
        //act
        controller.downLoadFile(fileName);
        // assert
        Mockito.verify(filesService, Mockito.times(wantedNumberInvocationInt)).downLoadFile(fileForRequestQueryDto);
    }

}
