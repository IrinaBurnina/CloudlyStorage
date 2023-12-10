package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class FileForResponseGetDto {
    @JsonProperty(value = "filename")
    private String fileName;
    private Integer size;
}
