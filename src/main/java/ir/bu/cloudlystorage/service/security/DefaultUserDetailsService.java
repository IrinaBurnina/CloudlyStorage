package ir.bu.cloudlystorage.service.security;

import ir.bu.cloudlystorage.mapper.UserDetailsMapper;
import ir.bu.cloudlystorage.service.CloudUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
    private final CloudUserService userService;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userService.getByLogin(username);
        return userDetailsMapper.mapUserToUserDetails(user);
    }
}

