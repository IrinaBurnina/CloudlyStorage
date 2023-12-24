package ir.bu.cloudlystorage.dto;

import lombok.Builder;

@Builder
public record UserDto(String login, String password) {
}
