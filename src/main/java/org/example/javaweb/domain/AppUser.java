package org.example.javaweb.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 200)
    private String passwordHash;

    // npr: "ROLE_USER" ili "ROLE_ADMIN"
    @Column(nullable = false, length = 50)
    private String role;

    @Column(nullable = false)
    private boolean enabled;
}
