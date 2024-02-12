package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FileForResponseGetDto(@JsonProperty(value = "filename") String fileName, Integer size) {
}
