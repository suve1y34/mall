package com.intea.domain.repository;

import com.intea.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findBySmall_ct(String c_code, Pageable pageable);
    Page<Product> findAllByLarge_ct(String large_ct, Pageable pageable);


}
