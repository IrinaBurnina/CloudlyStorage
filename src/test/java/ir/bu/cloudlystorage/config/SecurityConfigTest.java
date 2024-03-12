package ir.bu.cloudlystorage.config;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.exception.TokenNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.security.JwtTokenProvider;
import ir.bu.cloudlystorage.security.UserJwtAuthenticationFilter;
import ir.bu.cloudlystorage.service.CloudUserService;
import ir.bu.cloudlystorage.service.security.DefaultUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

public class SecurityConfigTest {
    CloudUserService userService = Mockito.mock(CloudUserService.class);
    JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);
    DefaultUserDetailsService customUserDerailsService = Mockito.mock(DefaultUserDetailsService.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HandlerExceptionResolver resolver = Mockito.mock(HandlerExceptionResolver.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    FilterChain filterChain = Mockito.mock(FilterChain.class);

    @Test
    public void doFilterInternalTest() throws IOException, ServletException {
        //arrange
        String jwt = "Bearer 00002222";
        Mockito.when(request.getHeader("auth-token")).thenReturn(jwt);
        int wantedNumberInvocationInt = 1;
        CloudUser cloudUser = new CloudUser();
        Optional<CloudUser> optionalCloudUser = Optional.of(cloudUser);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                cloudUser, null, cloudUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(userService.findByToken(new TokenDto("00002222"))).thenReturn(optionalCloudUser);
        UserJwtAuthenticationFilter userJwtAuthenticationFilter = new UserJwtAuthenticationFilter(
                jwtTokenProvider,
                customUserDerailsService,
                resolver);
        //act
        userJwtAuthenticationFilter.doFilter(request, response, filterChain);
        //assert
        Mockito.verify(securityContext, Mockito.times(wantedNumberInvocationInt))
                .setAuthentication(authentication);
    }

    @Test
    public void doFilterInternalTokeNotFoundExceptionTest() throws ServletException, IOException {
        //arrange
        int wantedNumberInvocationInt = 1;
        Mockito.when(request.getRequestURI()).thenReturn("/file");
        UserJwtAuthenticationFilter userJwtAuthenticationFilter = new UserJwtAuthenticationFilter(
                jwtTokenProvider,
                customUserDerailsService,
                resolver);
        //act
        userJwtAuthenticationFilter.doFilter(request, response, filterChain);
        //assert
        Mockito.verify(resolver, Mockito.times(wantedNumberInvocationInt))
                .resolveException(request, response, null, new TokenNotFoundException("Token not found"));
    }

    @Test
    public void doFilterInternalTokenNotFoundExceptionTest() throws ServletException, IOException {
        //arrange
        int wantedNumberInvocationInt = 1;
        String token = "Bearer 00002222";
        Mockito.when(request.getHeader("auth-token")).thenReturn(token);
        Mockito.when(userService.findByToken(new TokenDto("00002222"))).thenReturn(Optional.empty());
        UserJwtAuthenticationFilter userJwtAuthenticationFilter = new UserJwtAuthenticationFilter(
                jwtTokenProvider,
                customUserDerailsService,
                resolver);
        //act
        userJwtAuthenticationFilter.doFilter(request, response, filterChain);
        //assert
        Mockito.verify(resolver, Mockito.times(wantedNumberInvocationInt))
                .resolveException(request, response, null, new TokenNotFoundException("Token not found"));
    }
}
