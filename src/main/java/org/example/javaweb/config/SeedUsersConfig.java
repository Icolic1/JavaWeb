package org.example.javaweb.config;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.AppUser;
import org.example.javaweb.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SeedUsersConfig {

    @Bean
    CommandLineRunner seedUsers(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (!users.existsByUsername("admin")) {
                users.save(AppUser.builder()
                        .username("admin")
                        .passwordHash(encoder.encode("admin"))
                        .role("ROLE_ADMIN")
                        .enabled(true)
                        .build());
            }
        };
    }
}
