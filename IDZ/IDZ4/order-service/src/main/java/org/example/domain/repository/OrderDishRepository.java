package org.example.domain.repository;

import org.example.domain.entity.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderDish repository
 */
public interface OrderDishRepository extends JpaRepository<OrderDish, Integer> {
}
