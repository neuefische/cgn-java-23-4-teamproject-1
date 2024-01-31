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

            if (principal.getAttribute("iss") != null && principal.getAttribute("iss").toString().contains("accounts.google.com")) {
                System.out.println("Security: " + oAuth2User.getAttribute("email"));
                System.out.println("Principal: " + principal.getAttribute("email"));
            } else {
                System.out.println("Security: " + oAuth2User.getAttribute("login"));
                System.out.println("Principal: " + principal.getAttribute("login"));
            }
        }

        if (principal.getAttribute("iss").toString().contains("accounts.google.com")) {
            return principal.getAttribute("email");
        } else {
            return principal.getAttribute("login");
        }
    }
}
