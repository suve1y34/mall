package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
public class UserResponseDto {
    private UUID id;
    private String memId;
    private String email;
    private String name;
    private String phone;
    private String picture;
    private String postCode;
    private String address;
    private String deAddress;
    private Integer saving;

    @Getter
    @Setter
    @Builder
    @ToString
    public static class ReviewUserResponseDto {
        private String userId;
    }
}
