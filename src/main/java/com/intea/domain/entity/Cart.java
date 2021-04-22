package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.domain.dto.CartResponseDto;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer count;
    private Character useYn;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Orders orders;

    public CartResponseDto toResponseDto(int disPrice) {

        return CartResponseDto.builder()
                .id(id)
                .user(user)
                .product(product)
                .salePrice((int)((((float) 100 - (float) disPrice) / (float)100) * product.getPrice()))
                .count(count)
                .build();
    }
}
