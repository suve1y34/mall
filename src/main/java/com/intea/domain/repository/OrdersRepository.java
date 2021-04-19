package com.intea.domain.repository;

import com.intea.domain.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findAllByUserIdOrderByInsertTimeDesc(UUID userId, Pageable pageable);
}
