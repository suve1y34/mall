package com.intea.domain.repository;

import com.intea.domain.entity.ProductDisPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductDisPriceRepository extends JpaRepository<ProductDisPrice, Long> {
/*    @Query("SELECT new Map(p.id AD id, p.product AS rpdocut," +
            "MAX(p.disPrice) AS disPrice)" +
            "FROM ProductDisPrice p" +
            "WHERE NOW() BETWEEN p.startDate p.endDate" +
            "GROUP BY p.product")
    Page<Map<String, Object>> getSaleProductList(Pageable pageable);*/

    List<ProductDisPrice> findByProductId(Long id);
}
