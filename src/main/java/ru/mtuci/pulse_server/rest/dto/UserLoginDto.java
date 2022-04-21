package ru.mtuci.pulse_server.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    private String login;
    private String password;
}
