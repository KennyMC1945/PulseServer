package ru.mtuci.pulse_server.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.pulse_server.model.User;
import ru.mtuci.pulse_server.rest.dto.UserDto;
import ru.mtuci.pulse_server.rest.dto.UserLoginDto;
import ru.mtuci.pulse_server.service.UserService;

@RestController
@RequestMapping(("/api/v1"))
@RequiredArgsConstructor
public class ApiV1Controller {
    private static final String TOKEN_HEADER = "AUTH-TOKEN-X";

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);
        return ResponseEntity.ok().body(String.format("token: %s.%s", user.getLogin(), user.getHashPassword()));
    }

    @GetMapping("/user/authorize")
    public ResponseEntity<String> authorize(@RequestBody UserLoginDto loginDto) {
        String token = userService.authorize(loginDto);
        return ResponseEntity.ok().body(String.format("Successful authorization! Token: %s", token));
    }

    @GetMapping("/steps")
    public ResponseEntity<String> getTodaySteps(@RequestHeader(TOKEN_HEADER) String token) {
        Integer steps = userService.getTodaySteps(token);
        return ResponseEntity.ok().body(String.format("Your steps amount for today is %s", steps));
    }

    @PutMapping("/steps")
    public ResponseEntity<Void> updateStepsCount(@RequestHeader(TOKEN_HEADER) String token, @RequestParam Integer stepCount) {
        userService.updateStepsCount(token, stepCount);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AssertionError.class)
    public ResponseEntity<String> handleAssertionError(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================


}
