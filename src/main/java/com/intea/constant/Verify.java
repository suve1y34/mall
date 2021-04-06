package com.intea.constant;

public enum Verify {

    ADMIN("관리자"),
    MEMBER("일반회원");

    private String verify;

    Verify(String verify) {
        this.verify = verify;
    }

    public String getValue() {
        return verify;
    }
}
