package ir.bu.cloudlystorage.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"login", "password"})
@Table(name = "users", schema = "diploma")
public class CloudUser implements UserDetails {
    @Id
    private String login;
    private String password;
    private String token;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Role> roles = List.of();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList();
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
