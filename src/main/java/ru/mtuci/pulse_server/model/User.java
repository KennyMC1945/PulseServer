package ru.mtuci.pulse_server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    @NonNull
    private String firstName;

    private String lastName;

    private Integer age;

    private Integer weight;

    private int hashPassword;

    @OneToMany(mappedBy = "user")
    private List<Pulse> bpmData;

    @OneToMany(mappedBy = "user")
    private List<Steps> stepsData;
}
