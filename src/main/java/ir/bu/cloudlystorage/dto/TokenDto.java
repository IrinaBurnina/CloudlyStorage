package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TokenDto {
    @JsonProperty(value = "auth-token")
    private String authToken;

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
