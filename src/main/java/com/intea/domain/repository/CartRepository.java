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
    List<Cart> findAllByUserIdAndUseYnOrderByInsertTimeDesc(UUID userId, char useYn);
    Page<Cart> findAllByUserIdAndUseYnOrderByInsertTimeDesc(UUID userId, char useYn, Pageable pageable);
    List<Cart> findAllByUserIdAndProductId(UUID userId, Long productId);
}
