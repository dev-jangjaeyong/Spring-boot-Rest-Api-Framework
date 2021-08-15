package com.atonm.kblease.api.repository;

import com.atonm.kblease.api.common.entity.TaMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
public interface UserJpaRepository extends JpaRepository<TaMember, Long> {
    @EntityGraph(attributePaths = "userRoles")
    Optional<TaMember> findByUserId(String userId);
}
