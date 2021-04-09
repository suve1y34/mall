package com.intea.domain.entity;

import com.intea.constant.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@Entity
public class Orders extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String order_num;
    private String order_name;
    private String message;
    private OrderStatus status;
    private String postCode;
    private String address;
    private String de_address;
    private String phone;
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "pd_orders", cascade = CascadeType.ALL)
    private List<Cart> cartList;
}
