package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TokenDto {
    @JsonProperty(value = "auth-token")
    private String authToken;

    public String generate() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }
}
