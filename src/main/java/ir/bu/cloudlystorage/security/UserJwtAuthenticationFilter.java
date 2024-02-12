package ir.bu.cloudlystorage.security;

import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.exception.TokenNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.service.CloudUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserJwtAuthenticationFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION = "auth-token";
    private static final String PREFIX_HEADER = "Bearer ";
    private final CloudUserService service;
    private final JwtTokenProvider tokenProvider;
    ;
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = getJwtTokenFromRequest(request);
        if (jwt != null && tokenProvider.validateToken(jwt)) {
            Optional<CloudUser> userOptional = service
                    .findByToken(new TokenDto(jwt));
            if (userOptional.isPresent()) {
                CloudUser cloudUser = userOptional.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        cloudUser, null, cloudUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                resolver.resolveException(request, response, null, new TokenNotFoundException("Token not found"));
                return;
            }
        } else if (!request.getRequestURI().equals("/login")) {
            resolver.resolveException(request, response, null, new TokenNotFoundException("Token not found"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authToken) && authToken.startsWith(PREFIX_HEADER)) {
            return authToken.substring(7);
        }
        return null;
    }
}
