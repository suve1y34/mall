package com.intea.domain.entity;

import com.intea.constant.OrderStatus;
import com.intea.domain.dto.OrdersResponseDto;
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

    private String orderNum;
    private String orderNm;
    private String message;
    private OrderStatus status;
    private String postCode;
    private String address;
    private String deAddress;
    private String phone;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "productOrders", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    public OrdersResponseDto toResponseDTO() {
        LocalDateTime insertTime = this.getInsertTime();

        return OrdersResponseDto.builder()
                .id(id)
                .orderNum(orderNum)
                .orderNm(orderNm)
                .message(message)
                .status(status.getValue())
                .address(address)
                .amount(amount)
                .insertTime(insertTime.getYear() + "." + insertTime.getMonthValue() + "."
                +insertTime.getDayOfMonth() + " " + insertTime.getHour() + ":" + insertTime.getMinute() + ":" + insertTime.getSecond())
                .cartList(cartList)
                .build();
    }
}
