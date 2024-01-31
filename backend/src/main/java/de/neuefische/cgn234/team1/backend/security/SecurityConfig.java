package de.neuefische.cgn234.team1.backend.security;

import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepo userRepo;

    public SecurityConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Value("${app.enviroment}")
    private String environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/user/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/upload/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/workouts").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/workouts/generate").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/workouts/*").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/workouts/*").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/user/addWorkout").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/deleteWorkout").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/deleteUser").authenticated()
                                .anyRequest().permitAll())
                .oauth2Login(o -> {
                    try {
                        o.init(http);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    o.defaultSuccessUrl("http://localhost:5173/", true);
                });
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User user = delegate.loadUser(request);

            if (!userRepo.existsUserByUserName(user.getName())) {
                User newUser = new User(user.getAttributes());
                userRepository.save(newUser);
            }

            return user;
        };
    }
}

}
