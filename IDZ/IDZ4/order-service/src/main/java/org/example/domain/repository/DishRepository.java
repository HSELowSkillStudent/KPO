package org.example.domain.repository;

import org.example.domain.entity.Dish;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dish repository
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {
    /**
     * Check if dish exists by name
     *
     * @param name dish name
     * @return true if dish exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Find dish by name
     * @param name dish name
     * @return dish
     */
    Dish findByName(String name);

    /**
     * Delete dish by name
     * @param dishName dish name
     */

    @Transactional
    void deleteByName(String dishName);
}
