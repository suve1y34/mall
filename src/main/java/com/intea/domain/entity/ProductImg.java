package com.intea.domain.entity;

import com.intea.common.CommonEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductImg extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private Character basicYn;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
