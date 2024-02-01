package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserRequest;
import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PutMapping("/addWorkout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse addWorkoutToUser(@RequestParam String userName, @RequestBody UserRequest userRequest) {
        return userService.addWorkoutToUser(userRequest);
    }

    @DeleteMapping("/deleteWorkout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponse deleteWorkoutFromUser(@RequestParam String userName, @RequestBody UserWorkout workout) {
        return userService.deleteWorkoutFromUser(userName, workout);
    }

    @DeleteMapping("/deleteUser")
    public boolean deleteUser(@RequestParam String userName) {
        return userService.deleteUser(userName);
    }

    @GetMapping("/me")
    public User getMe() throws IllegalArgumentException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken token) {
            return userService.getUser(token.getPrincipal().getAttributes().get("login").toString());
        }


        throw new IllegalArgumentException("Not logged in");
    }


}
/* if (principal == null) {
        return null;
        }

Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principalObject instanceof OAuth2User) {
OAuth2User oAuth2User = (OAuth2User) principalObject;

            if (principal.getAttribute("iss") != null) {
        if (principal.getAttribute("iss").toString().contains("accounts.google.com")) {
        System.out.println("Security: " + oAuth2User.getAttribute("email"));
        System.out.println("Principal: " + principal.getAttribute("email"));
        }
        } else {
        System.out.println("Security: " + oAuth2User.getAttribute("login"));
        System.out.println("Principal: " + principal.getAttribute("login"));
        }
        }

        if (principal.getAttribute("iss") != null) {
        if (principal.getAttribute("iss").toString().contains("accounts.google.com")) {
        return principal.getAttribute("email");
            }
                    }
                    return principal.getAttribute("login");
    }*/
