package com.intea.domain.entity;

import com.intea.constant.OrderStatus;
import com.intea.domain.dto.OrdersResDTO;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "pd_orders", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    public OrdersResDTO toResponseDTO() {
        LocalDateTime insert_time = this.getInsert_time();

        return OrdersResDTO.builder()
                .id(id)
                .order_num(order_num)
                .order_name(order_name)
                .message(message)
                .status(status.getValue())
                .address(address)
                .amount(amount)
                .insert_time(insert_time.getYear() + "." + insert_time.getMonthValue() + "."
                +insert_time.getDayOfMonth() + " " + insert_time.getHour() + ":" + insert_time.getMinute() + ":" + insert_time.getSecond())
                .cartList(cartList)
                .build();
    }
}
