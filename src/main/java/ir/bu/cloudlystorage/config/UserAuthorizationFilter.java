package ir.bu.cloudlystorage.config;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.exception.TokenNotFoundException;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.service.CloudUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class UserAuthorizationFilter extends OncePerRequestFilter {
    private static final String PREFIX_HEADER = "Bearer ";
    private final CloudUserService service;
    private final HandlerExceptionResolver resolver;

    public UserAuthorizationFilter(CloudUserService service, HandlerExceptionResolver resolver) {
        this.service = service;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String gettingToken = getTokenFromRequest(request);
        if (gettingToken != null && !"".equals(gettingToken.trim())
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<CloudUser> userOptional = service.findByToken(new TokenDto(gettingToken));
            if (userOptional.isPresent()) {
                CloudUser user = userOptional.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
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

    private String getTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authToken) && authToken.startsWith(PREFIX_HEADER)) {
            return authToken.substring(7);
        }
        return null;
    }
}
