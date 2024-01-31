package de.neuefische.cgn234.team1.backend.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("me")
    public String getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return null;
        }

        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principalObject instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principalObject;

            System.out.println("Secruity: " + oAuth2User.getAttribute("login"));
            System.out.println("Principal: " + principal.getAttribute("login"));
        }

        return principal.getAttribute("login");
    }
}
