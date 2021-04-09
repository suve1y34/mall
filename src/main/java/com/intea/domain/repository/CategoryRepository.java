package com.intea.domain.repository;

import com.intea.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByC_levelOrderByC_codeDesc(int idx);
    List<Category> findAllByUpper_c_codeOrderByC_codeDesc(String upper_c_code);
    Category findByC_code(String upper_c_code);
    List<Category> findAllByUpper_c_code(String large_ct);
}
