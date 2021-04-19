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
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @JsonIgnore
    private Orders orders;

    public CartResponseDto toResponseDto() {

        return CartResponseDto.builder()
                .id(id)
                .user(user)
                .product(product)
                .count(count)
                .build();
    }
}
