package ru.mtuci.pulse_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.pulse_server.model.Steps;
import ru.mtuci.pulse_server.model.User;
import ru.mtuci.pulse_server.repo.PulseJpaRepository;
import ru.mtuci.pulse_server.repo.StepsJpaRepository;
import ru.mtuci.pulse_server.repo.UserJpaRepository;
import ru.mtuci.pulse_server.rest.dto.UserDto;
import ru.mtuci.pulse_server.rest.dto.UserLoginDto;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserJpaRepository userRepo;
    private final StepsJpaRepository stepsRepo;
    private final PulseJpaRepository pulseRepo;

    public User addUser(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setWeight(userDto.getWeight());
        user.setHashPassword(userDto.getPassword().hashCode());
        return userRepo.save(user);
    }

    public String authorize(UserLoginDto userLoginDto) {
        User user = userRepo.findByLogin(userLoginDto.getLogin());
        if (user != null && user.getHashPassword() == userLoginDto.getPassword().hashCode()) {
            return String.format("%s.%d", user.getLogin(), user.getHashPassword());
        } else {
            throw new IllegalArgumentException("Wrong Pass or Login!");
        }
    }

    public Integer getTodaySteps(String token) {
        User user = checkToken(token);
        Optional<Steps> stepData = user.getStepsData().stream().filter(steps -> steps.getDate().equals(LocalDate.now())).findFirst();
        return stepData.isPresent() ? stepData.get().getStepsCount() : 0;
    }

    @Transactional
    public void updateStepsCount(String token, Integer stepAmount) {
        User user = checkToken(token);
        Optional<Steps> stepData = user.getStepsData().stream().filter(steps -> steps.getDate().equals(LocalDate.now())).findFirst();
        if (stepData.isEmpty()) {
            Steps steps = new Steps();
            steps.setDate(LocalDate.now());
            steps.setStepsCount(stepAmount);
            user.getStepsData().add(steps);
            steps.setUser(user);
            stepsRepo.save(steps);
        } else {
            Integer currentAmount = stepData.get().getStepsCount();
            stepData.get().setStepsCount(currentAmount + stepAmount);
        }
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(String token) {
        User user = checkToken(token);
        userRepo.deleteById(user.getId());
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private User checkToken(String token) {
        String[] tokenData = token.split("\\.");
        assert tokenData.length == 2 : "Invalid Token";
        String login = tokenData[0];
        User user = userRepo.findByLogin(login);
        assert user != null : "No such user";
        assert user.getHashPassword() == Integer.parseInt(tokenData[1]) : "Wrong password";
        return user;
    }
}
