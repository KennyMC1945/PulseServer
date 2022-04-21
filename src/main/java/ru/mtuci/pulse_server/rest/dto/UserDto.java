package ru.mtuci.pulse_server.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer weight;
    private String password;
}
