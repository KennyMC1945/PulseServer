package ru.mtuci.pulse_server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.pulse_server.model.Pulse;

@Repository
public interface PulseJpaRepository extends JpaRepository<Pulse, Long> {
}
