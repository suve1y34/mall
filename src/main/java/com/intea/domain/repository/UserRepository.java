package com.intea.domain.repository;

import com.intea.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
/*    Optional<Members> findByI_mem(Long i_mem);
    Optional<Members> findByMem_id(String mem_id);
    boolean existsByMem_id(String mem_id);*/
    Optional<User> findByEmail(String email);
}