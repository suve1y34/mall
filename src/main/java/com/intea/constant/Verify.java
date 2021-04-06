package com.intea.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Verify {

    MEMBER("MEMBER", "일반 사용자"),
    ADMIN("ADMIN", "관리자");

    private final String key;
    private final String title;
}
