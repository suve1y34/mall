package com.intea.domain.repository;

import com.intea.domain.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_idAnAndUse_ynOrderByInsert_timeDesc(UUID user_id, char use_yn);
    Page<Cart> findAllByUser_idAnAndUse_ynOrderByInsert_timeDesc(UUID user_id, char use_yn, Pageable pageable);

    List<Cart> findAllByUser_idAndProduct_id(UUID user_id, Long product_id);
}
