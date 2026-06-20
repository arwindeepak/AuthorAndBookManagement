package com.module5.AuthorAndBookManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails librarian = User.builder()
                .username("librarian")
                .password(passwordEncoder.encode("lib123"))
                .roles("LIBRARIAN")
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                librarian
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                      // swagger authentication button
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Author APIs
                        .requestMatchers(HttpMethod.GET,
                                "/api/authors/**")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/authors/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/authors/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/authors/**")
                        .hasRole("ADMIN")

                        // Book APIs
                        .requestMatchers(HttpMethod.GET,
                                "/api/books/**")
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(HttpMethod.POST,
                                "/api/books/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/books/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/books/**")
                        .hasRole("ADMIN")

                        // Swagger


                        .anyRequest()
                        .authenticated()

                )

                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
