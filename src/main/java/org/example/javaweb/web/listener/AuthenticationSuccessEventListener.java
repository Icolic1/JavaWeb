package org.example.javaweb.web.listener;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.LoginAudit;
import org.example.javaweb.repository.LoginAuditRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessEventListener {

    private final LoginAuditRepository loginAuditRepository;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        if (auth == null || auth.getName() == null) return;

        String ip = extractIp(auth);

        loginAuditRepository.save(LoginAudit.builder()
                .username(auth.getName())
                .ipAddress(ip)
                .build());
    }

    private String extractIp(Authentication auth) {
        Object details = auth.getDetails();
        if (details instanceof WebAuthenticationDetails webDetails) {
            return webDetails.getRemoteAddress();
        }
        return "UNKNOWN";
    }
}
