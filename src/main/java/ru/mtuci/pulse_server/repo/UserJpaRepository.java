package ru.mtuci.pulse_server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.pulse_server.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
