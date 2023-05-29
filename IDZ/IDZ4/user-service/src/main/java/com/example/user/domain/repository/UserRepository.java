package com.example.user.domain.repository;

import com.example.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * existsByEmail
     * @param email email
     * @return True if exists else False
     */
    boolean existsByEmail(String email);

    /**
     * findByEmail
     * @param email email
     * @return User
     */
    User findByEmail(String email);

    /**
     * existsByUserName
     * @param userName userName
     * @return True if exists else False
     */
    boolean existsByUsername(String userName);

    /**
     * findByUserName
     * @param userName userName
     * @return User
     */
    User findByUsername(String userName);
}
