package com.intea.domain.repository;

import com.intea.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(UUID id);
    Optional<User> findByMem_id(String mem_id);
    boolean existsByMem_id(String mem_id);
}