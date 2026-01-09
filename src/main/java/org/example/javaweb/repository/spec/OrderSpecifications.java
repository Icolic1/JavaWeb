package org.example.javaweb.repository.spec;

import org.example.javaweb.domain.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class OrderSpecifications {

    private OrderSpecifications() {}

    public static Specification<Order> usernameContainsIgnoreCase(String username) {
        return (root, query, cb) -> {
            if (username == null || username.trim().isEmpty()) {
                return cb.conjunction();
            }
            String like = "%" + username.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("username")), like);
        };
    }

    public static Specification<Order> createdAtFrom(LocalDate from) {
        return (root, query, cb) -> {
            if (from == null) {
                return cb.conjunction();
            }
            LocalDateTime fromDt = from.atStartOfDay();
            return cb.greaterThanOrEqualTo(root.get("createdAt"), fromDt);
        };
    }

    public static Specification<Order> createdAtTo(LocalDate to) {
        return (root, query, cb) -> {
            if (to == null) {
                return cb.conjunction();
            }
            // uključivo do kraja dana
            LocalDateTime toDt = to.plusDays(1).atStartOfDay();
            return cb.lessThan(root.get("createdAt"), toDt);
        };
    }
}
