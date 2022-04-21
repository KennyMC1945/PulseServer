package ru.mtuci.pulse_server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Pulse {

    @Id
    @GeneratedValue
    private Long pulseId;

    private LocalDateTime date;

    private Integer bpm;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
