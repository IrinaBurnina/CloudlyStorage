package ir.bu.cloudlystorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileForRequestDto(@JsonProperty(value = "filename") String fileName) {
}
