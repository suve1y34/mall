package com.intea.domain.repository;

import com.intea.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends CrudRepository<Members, String> {
    Optional<Members> findByI_mem(Long i_mem);
    Optional<Members> findByNm(String nm);
    Optional<Members> findByNmLike(String keyword);
}