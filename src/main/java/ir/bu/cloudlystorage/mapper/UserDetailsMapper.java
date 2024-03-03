package ir.bu.cloudlystorage.mapper;

import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {
    public UserDetails mapUserToUserDetails(CloudUser user) {
        return new User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList()
        );
    }
}
