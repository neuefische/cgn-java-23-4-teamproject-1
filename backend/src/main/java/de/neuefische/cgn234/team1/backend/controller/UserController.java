package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.user.User;
import de.neuefische.cgn234.team1.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me")
    public String getLoggedInUserEmail(@AuthenticationPrincipal OAuth2User user) {
        User currentUser = userService.getUser(user);
        if (currentUser != null) {
            return currentUser.email();
        }
        return "anonymousUser";
    }
}
