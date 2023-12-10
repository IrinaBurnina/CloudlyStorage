package ir.bu.cloudlystorage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"login", "password", "token"})
@Table(name = "diploma", schema = "login")
public class CloudUser implements UserDetails {
    @EmbeddedId
    @Column(name = "login")
    @NotNull
    private String username;
    @NotNull
    @Column
    private String password;
    @Column
    private String token;
    @Column
    private boolean enabled;

    @Override
    public String toString() {
        return "CloudUser{" +
                "login='" + username + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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
