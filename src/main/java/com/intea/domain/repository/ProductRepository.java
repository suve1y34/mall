package com.intea.domain.repository;

import com.intea.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findBySmallCat(String catCode, Pageable pageable);
    Page<Product> findAllByLargeCat(String largeCat, Pageable pageable);
    Page<Product> findByLargeCatAndSmallCatOrderByInsertTimeDesc(String largeCat, String smallCat, Pageable pageable);
}
