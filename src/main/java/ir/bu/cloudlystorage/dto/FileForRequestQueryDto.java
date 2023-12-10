package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class FileForRequestQueryDto {
    @JsonProperty(value = "filename")
    private String fileName;
}
