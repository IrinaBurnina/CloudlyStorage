package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.service.CloudUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthorizationControllerTest {
    CloudUserService userService = Mockito.mock(CloudUserService.class);
    AuthorizationController authController = new AuthorizationControllerImpl(userService);
    String token = "00002222";

    @Test
    public void loginTest() {
        //arrange
        UserDto userDtoTest = new UserDto("kot", "kot");
        TokenDto tokenDtoExpected = new TokenDto("00002222");
        Mockito.when(userService.loginAndGetToken(userDtoTest)).thenReturn(tokenDtoExpected);
        //act
        TokenDto tokenDto = authController.login(userDtoTest);
        //assert
        Assertions.assertEquals(tokenDtoExpected, tokenDto);
    }

    @Test
    public void logoutTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        //act
        authController.logout(token);
        // assert
        Mockito.verify(userService, Mockito.times(wantedNumberInvocationInt)).logout(token);
    }
}
