package org.example.javaweb.web.dto;

import java.time.LocalDateTime;

public record LoginAuditListDto(
        Long id,
        String username,
        String ipAddress,
        LocalDateTime loginAt
) {}
