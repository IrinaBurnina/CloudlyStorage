package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.exception.UserNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import ir.bu.cloudlystorage.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class ServiceTest {
    TokenDto token = Mockito.mock(TokenDto.class);
    UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);
    CloudUserService service = new CloudUserServiceImpl(authenticationManager, usersRepository, jwtTokenProvider);

    @Test
    public void findByTokenTest() {
        //arrange
        String token = "00002222";
        CloudUser cloudUser = new CloudUser();
        Optional<CloudUser> expectedOptionalCloudUser = Optional.of(cloudUser);
        Mockito.when(usersRepository.getUserByToken(token)).thenReturn(expectedOptionalCloudUser);
        TokenDto tokenDto = new TokenDto(token);
        //act
        Optional<CloudUser> actualOptionalCloudUser = service.findByToken(tokenDto);
        //assert
        Assertions.assertEquals(actualOptionalCloudUser, expectedOptionalCloudUser);
    }

    @Test
    public void loginAndGetTokenTest() {
        //arrange
        UserDto userDto = new UserDto("kot", "kot");
        var authenticationToken = new UsernamePasswordAuthenticationToken(userDto.login(), userDto.password());
        Authentication authentication = Mockito.mock(Authentication.class);
        CloudUser cloudUser = new CloudUser("kot", "kot", null, true, null);
        String expectedToken = "00002222";
        Mockito.when(authentication.getPrincipal()).thenReturn(cloudUser);
        Mockito.when(jwtTokenProvider.generateAccessToken(authentication)).thenReturn(expectedToken);
        Mockito.when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        //act
        String tokenActual = service.loginAndGetToken(userDto);
        // assert
        Assertions.assertEquals(expectedToken, tokenActual);
    }

    @Test
    public void logoutTest() {
        //arrange
        int wantedNumberInvocationInt = 1;
        String tokenWithBearer = "Bearer 00002222";
        String token = "00002222";
        CloudUser cloudUser = new CloudUser();
        Optional<CloudUser> optionalCloudUser = Optional.of(cloudUser);
        Mockito.when(usersRepository.getUserByToken(token)).thenReturn(optionalCloudUser);
        //act
        service.logout(tokenWithBearer);
        //assert
        Mockito.verify(usersRepository, Mockito.times(wantedNumberInvocationInt)).save(cloudUser);
    }

    @Test
    public void exceptionUserNotFoundLogoutTest() {
        //arrange
        String tokenWithBearer = "Bearer 00004444";
        String token = "00002222";
        CloudUser cloudUser = new CloudUser();
        Optional<CloudUser> optionalCloudUser = Optional.of(cloudUser);
        Mockito.when(usersRepository.getUserByToken(token)).thenReturn(optionalCloudUser);
        //act
        Executable action = () -> service.logout(tokenWithBearer);
        //assert
        Assertions.assertThrowsExactly(UserNotFoundException.class, action);
    }
}
