package org.example.domain.repository;

import org.example.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Order repository
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
