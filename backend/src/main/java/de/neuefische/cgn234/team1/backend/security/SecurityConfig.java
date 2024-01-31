package de.neuefische.cgn234.team1.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstratctHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/user").authenticated()
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
}
