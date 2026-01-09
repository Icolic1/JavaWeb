package org.example.javaweb.web.mapper;

import org.example.javaweb.domain.LoginAudit;
import org.example.javaweb.web.dto.LoginAuditListDto;

public final class LoginAuditMapper {

    private LoginAuditMapper() {}

    public static LoginAuditListDto toListDto(LoginAudit a) {
        return new LoginAuditListDto(
                a.getId(),
                a.getUsername(),
                a.getIpAddress(),
                a.getLoginAt()
        );
    }
}
