package org.example.javaweb.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 64)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime loginAt;

    @PrePersist
    void onCreate() {
        if (loginAt == null) loginAt = LocalDateTime.now();
    }
}
