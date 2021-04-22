package com.intea.domain.repository;

import com.intea.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCtLevelOrderByCatCodeDesc(int idx);
    List<Category> findAllByUpperCatCodeOrderByCatCodeDesc(String upperCatCode);
    List<Category> findAllByUseYn(char yn);
    Category findByCatCode(String upperCatCode);
    List<Category> findAllByUpperCatCode(String largeCat);
}
