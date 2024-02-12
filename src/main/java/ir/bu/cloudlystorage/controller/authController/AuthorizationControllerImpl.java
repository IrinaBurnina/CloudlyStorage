package ir.bu.cloudlystorage.controller.authController;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.service.CloudUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//TODO адаптировать тесты для вынесенного контроллера
@AllArgsConstructor
public class AuthorizationControllerImpl implements AuthorizationController {
    private final CloudUserService usersService;

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

}
