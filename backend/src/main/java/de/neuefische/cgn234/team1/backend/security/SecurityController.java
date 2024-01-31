package de.neuefische.cgn234.team1.backend.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user/")
public class SecurityController {

    @GetMapping("/me")
    public String getCurrentUser(@AuthenicationPrincipal Principal principal) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
        // return principal.getName();
    }
}
