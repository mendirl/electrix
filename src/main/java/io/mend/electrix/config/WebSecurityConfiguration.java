package io.mend.electrix.config;

import org.springframework.boot.security.autoconfigure.actuate.web.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
    @Order(1)
    public SecurityFilterChain actuator(HttpSecurity http) {
        return http.securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(authorize -> authorize.anyRequest()
//                .hasRole("ENDPOINT_ADMIN")
                    .permitAll()
            ).build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain all(HttpSecurity http) {
        return http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll()).build();
    }

}
