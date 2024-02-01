package de.neuefische.cgn234.team1.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.enviroment}")
    private String environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .anyRequest().permitAll()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(logout -> {
                    if (environment.equals("prod")) {
                        logout.logoutSuccessUrl("/").permitAll();
                    } else {
                        logout.logoutSuccessUrl("http://localhost:5173").permitAll();
                    }
                })
                .oauth2Login(c -> {
                    try {
                        c.init(http);
                        if (environment.equals("prod")) {
                            c.defaultSuccessUrl("/", true);
                        } else {
                            c.defaultSuccessUrl("http://localhost:5173", true);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .logout(l -> l.logoutUrl("/api/logout").logoutSuccessHandler(((request, response, authentication) -> response.setStatus(200))));
        return http.build();
    }

}
