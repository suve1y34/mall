package com.intea.domain.enums;

public enum OrderStatus {

    STANDBY("배송준비중"),
    BEGIN("배송중"),
    COMPLATE("배송완료"),
    EXCHANGE("교환"),
    RETURN("반품");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
