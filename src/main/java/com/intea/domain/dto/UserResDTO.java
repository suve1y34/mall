package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
public class UserResDTO {
    private UUID id;
    private String mem_id;
    private String email;
    private String name;
    private String phone;
    private String picture;
    private String postCode;
    private String address;
    private String de_address;
    private Integer saving;

    @Getter
    @Setter
    @Builder
    @ToString
    public static class ReviewUserResDTO {
        private String user_id;
    }
}
