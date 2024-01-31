package de.neuefische.cgn234.team1.backend.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user/")
public class SecurityController {

    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal Principal principal) {
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
}
