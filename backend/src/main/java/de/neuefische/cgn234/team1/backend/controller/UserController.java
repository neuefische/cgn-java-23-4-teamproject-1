package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.service.UserRequest;
import de.neuefische.cgn234.team1.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public boolean login(@RequestBody UserRequest userRequest) {
        return userService.login(userRequest.userName(), userRequest.password());
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public boolean logout(@RequestBody String userName) {
        return userService.logout(userName);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return userService.createNewUser(userRequest.userName(), userRequest.password());
    }

    @GetMapping("/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@RequestParam String userName, @RequestBody String password) {
        return userService.getUser(userName, password);
    }

    @PutMapping("/{userName}/addWorkout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse addWorkoutToUser(@RequestParam String userName, @RequestBody UserRequest userRequest) {
        return userService.addWorkoutToUser(userRequest);
    }

    @DeleteMapping("/{userName}/deleteWorkout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponse deleteWorkoutFromUser(@RequestParam String userName, @RequestBody UserWorkout workout) {
        return userService.deleteWorkoutFromUser(userName, workout);
    }

    @DeleteMapping("/delete/{userName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUser(@RequestParam String userName) {
        return userService.deleteUser(userName);
    }


}
