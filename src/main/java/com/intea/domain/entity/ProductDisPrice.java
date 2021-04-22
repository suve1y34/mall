package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.domain.dto.ProductDisPriceResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDisPrice extends CommonEntity implements Comparable<ProductDisPrice> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private Integer disPrice;

    @Column
    private Character rateYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    private Product product;

    // 가격으로 내림차순 하기 위한 compareTo 재정의
    @Override
    public int compareTo(ProductDisPrice o) {
        return Integer.compare(o.disPrice, this.disPrice);
    }

    public ProductDisPriceResponseDto toResponseDto() {

        String startMonthStr = startDate.getMonthValue() < 10 ? "0" + startDate.getMonthValue() : "" + startDate.getMonthValue();
        String startDayStr = startDate.getDayOfMonth() < 10 ? "0" + startDate.getDayOfMonth() : "" + startDate.getDayOfMonth();
        String endMonthStr = endDate.getMonthValue() < 10 ? "0" + endDate.getMonthValue() : "" + endDate.getMonthValue();
        String endDayStr = endDate.getDayOfMonth() < 10 ? "0" + endDate.getDayOfMonth() : "" + endDate.getDayOfMonth();

        return ProductDisPriceResponseDto.builder()
                .id(id)
                .productId(product.getId())
                .startDate(startDate.getYear() + "-" + startMonthStr + "-" + startDayStr)
                .endDate(endDate.getYear() + "-" + endMonthStr + "-" + endDayStr)
                .disPrice(disPrice)
                .build();
    }
}
