package io.mend.electrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/", "/index.html").permitAll()
            .anyRequest().authenticated()
        )
        .build();
    }

}
