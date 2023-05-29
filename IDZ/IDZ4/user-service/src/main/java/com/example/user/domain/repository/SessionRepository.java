package com.example.user.domain.repository;

import com.example.user.domain.entity.Session;
import com.example.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SessionRepository
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    /**
     * existsBySessionToken
     * @param refreshToken refreshToken
     * @return True if exists
     */
    boolean existsBySessionToken(String refreshToken);

    /**
     * findBySessionToken
     * @param refreshToken refreshToken
     * @return Session
     */
    Session findBySessionToken(String refreshToken);

    /**
     * existsByUserId
     * @param id id
     * @return True if exists else False
     */
    boolean existsByUserId(Integer id);

    /**
     * findByUserId
     * @param id id
     * @return Session
     */
    Session findByUserId(Integer id);

}
