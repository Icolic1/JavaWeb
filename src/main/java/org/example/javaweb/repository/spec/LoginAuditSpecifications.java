package org.example.javaweb.repository.spec;

import org.example.javaweb.domain.LoginAudit;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class LoginAuditSpecifications {

    private LoginAuditSpecifications() {}

    public static Specification<LoginAudit> usernameContainsIgnoreCase(String username) {
        return (root, query, cb) -> {
            if (username == null || username.trim().isEmpty()) return cb.conjunction();
            String like = "%" + username.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("username")), like);
        };
    }

    public static Specification<LoginAudit> loginAtFrom(LocalDate from) {
        return (root, query, cb) -> {
            if (from == null) return cb.conjunction();
            LocalDateTime fromDt = from.atStartOfDay();
            return cb.greaterThanOrEqualTo(root.get("loginAt"), fromDt);
        };
    }

    public static Specification<LoginAudit> loginAtTo(LocalDate to) {
        return (root, query, cb) -> {
            if (to == null) return cb.conjunction();
            LocalDateTime toDt = to.plusDays(1).atStartOfDay();
            return cb.lessThan(root.get("loginAt"), toDt);
        };
    }
}
