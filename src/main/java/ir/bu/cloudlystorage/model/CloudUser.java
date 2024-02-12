package ir.bu.cloudlystorage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"login", "password"})
@Table(name = "users", schema = "diploma")
public class CloudUser implements UserDetails {
    @Id
    private String login;
    private String password;
    private String token;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public CloudUser(String login, String password, String token, boolean enabled,
                     Collection<? extends GrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.token = token;
        this.enabled = enabled;
        if (authorities == null) {
            this.authorities = null;
        } else {
            this.authorities = new ArrayList<>(authorities);
        }
    }
//    public static CloudUser create( User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
//
//        return new CloudUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),
//                user.getEmail(), user.getPassword(), authorities);
//    }

    @Override
    public String toString() {
        return "CloudUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : new ArrayList<>(authorities);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
