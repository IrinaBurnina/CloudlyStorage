package ir.bu.cloudlystorage.dto;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public record UserDto(String login, String password) {

}
