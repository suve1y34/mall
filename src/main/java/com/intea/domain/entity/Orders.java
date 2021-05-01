package com.intea.domain.entity;

import com.intea.common.CommonEntity;
import com.intea.domain.enums.OrderStatus;
import com.intea.domain.dto.OrdersResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Character refundState;
    private String phone;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    public OrdersResponseDto toResponseDTO() {
        LocalDateTime insertTime = this.getInsertTime();

        return OrdersResponseDto.builder()
                .id(id)
                .orderNum(orderNum)
                .orderNm(orderNm)
                .message(message)
                .status(status.getValue())
                .refundState(refundState)
                .postCode(postCode)
                .address(address)
                .deAddress(deAddress)
                .amount(amount)
                .insertTime(insertTime.getYear() + "." + insertTime.getMonthValue() + "."
                +insertTime.getDayOfMonth() + " " + insertTime.getHour() + ":" + insertTime.getMinute() + ":" + insertTime.getSecond())
                .cartList(cartList)
                .build();
    }
}
