package org.example.javaweb.web.listener;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.LoginAudit;
import org.example.javaweb.repository.LoginAuditRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessEventListener {

    private final LoginAuditRepository loginAuditRepository;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        if (auth == null || auth.getName() == null) return;

        loginAuditRepository.save(LoginAudit.builder()
                .username(auth.getName())
                // Listener nema trivijalan IP bez dodatne konfiguracije → stavi placeholder
                .ipAddress("N/A")
                .build());
    }
}
