package org.example.javaweb.security;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.AppUser;
import org.example.javaweb.repository.AppUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final AppUserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(u.getRole()) // npr ROLE_USER
                .disabled(!u.isEnabled())
                .build();
    }
}
