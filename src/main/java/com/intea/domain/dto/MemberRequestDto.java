package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class MemberRequestDto {

    @NotBlank(message = "이름을 작성해 주세요.")
    private String name;

    @NotBlank(message = "이메일을 작성해 주세요.")
    @Email(message = "이메일 양식에 맞게 작성해 주세요.")
    private String email;

    private String picture;

    private String postCode;
    private String address;
    private String deAddress;
    private String phone;
}
