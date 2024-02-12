package ir.bu.cloudlystorage.dto.authDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TokenDto {
    @JsonProperty(value = "auth-token")
    private String authToken;
}
