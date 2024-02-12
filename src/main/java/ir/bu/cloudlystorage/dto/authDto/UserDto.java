package ir.bu.cloudlystorage.dto.authDto;

import lombok.Builder;

@Builder
public record UserDto(String login, String password) {
}
