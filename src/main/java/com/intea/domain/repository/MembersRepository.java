package com.intea.domain.repository;

import com.intea.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByI_mem(Long i_mem);
    Optional<Members> findByMem_id(String mem_id);
    boolean existsByMem_id(String mem_id);
}