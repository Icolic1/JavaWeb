package org.example.javaweb.repository;

import org.example.javaweb.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUsernameOrderByCreatedAtDesc(String username);

    List<Order> findAllByOrderByCreatedAtDesc();
}
