package de.neuefische.cgn234.team1.backend.security;


import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final UserRepo userRepo;

    public SecurityConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Value("${app.environment}")
    private String environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/user/me").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/upload/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/workouts").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/workouts/generate").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/workouts/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/workouts/*").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/user/addWorkout").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/deleteWorkout").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/deleteUser").authenticated()
                                .anyRequest().permitAll())
                .oauth2Login(c -> {
                    try {
                        c.init(http);
                        c.loginPage("/");
                        if (environment.equals("prod")) {
                            c.defaultSuccessUrl("/", true);
                        } else {
                            c.defaultSuccessUrl("http://localhost:5173", true);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .logout(logout -> {
                    if (environment.equals("prod")) {

                        logout.logoutSuccessUrl("/");
                    } else {
                        logout.logoutSuccessUrl("http://localhost:5173");
                    }
                });
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User user = delegate.loadUser(request);

            if (!userRepo.existsUserByUserName(user.getName())) {
                User newUser = new User(user.getName(), new ArrayList<>());
                userRepo.save(newUser);
            }

            return user;
        };
    }

}

