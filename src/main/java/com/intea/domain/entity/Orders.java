package com.intea.domain.entity;

import com.intea.constant.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class Orders extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_order;

    @ManyToOne
    @JoinColumn(name = "i_product", referencedColumnName = "i_product")
    private Product product;

    private String rec;
    private Long amount;
    private String postCode;
    private String address;
    private String de_address;
    private String phone;
    private OrderStatus order_status;
}
