package ir.bu.cloudlystorage.security;

import ir.bu.cloudlystorage.exception.TokenNotFoundException;
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
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Component
public class UserJwtAuthenticationFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION = "auth-token";
    private static final String PREFIX_HEADER = "Bearer ";
    private final CloudUserService service;//DefaultUserDetailsService
    private final JwtTokenProvider tokenProvider;//JwtGenerator
    ;
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = getJwtTokenFromRequest(request);
        if (jwt != null && tokenProvider.validateToken(jwt)) {
            final var username = tokenProvider.getUserLoginFromJWT(jwt);
            final var userDetails = service.getByLogin(username);
            final var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else if (!request.getRequestURI().equals("/login")) {
            resolver.resolveException(request, response, null, new TokenNotFoundException("Token not found"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION);
        return hasText(authToken) && authToken.startsWith(PREFIX_HEADER) ? authToken.substring(7) : null;
    }
}
