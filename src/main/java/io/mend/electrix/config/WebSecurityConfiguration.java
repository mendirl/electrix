package io.mend.electrix.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration {

//    @Bean
//    SecurityFilterChain index(HttpSecurity http) throws Exception {
//    return http
//        .authorizeHttpRequests(requests-> requests
//            .requestMatchers("/", "/index.html").permitAll()
//            .anyRequest().authenticated()
//        )
//        .build();
//    }

    @Bean
    public SecurityFilterChain actuator(HttpSecurity http) throws Exception {
        return http.

            authorizeHttpRequests(requests -> requests
            .requestMatchers(EndpointRequest.toAnyEndpoint())
//            .hasRole("ENDPOINT_ADMIN")
                           .permitAll()
                   )
//            .httpBasic(withDefaults())
        .build();
    }
    @Bean
    SecurityFilterChain all(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/**").permitAll()
        )
        .build();
    }

}
