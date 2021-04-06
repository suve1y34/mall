package com.intea.constant;

public enum ReviewStatus {

    GOOD("만족"),
    NORMAL("보통"),
    BAD("불만족");

    private String status;

    ReviewStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
