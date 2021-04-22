package com.intea.domain.dto;

import com.intea.domain.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9]).{8,12}$", message = "아이디는 영문+숫자 조합 8~12자리가 가능합니다.")
    private String memId;
    
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 주소 양식에 맞게 입력해주세요.")
    private String emailAddress;
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9`~!@#$%^&*()\\-_+=\\\\]).{8,15}$", message = "비밀번호는 영문+숫자+특수문자 조합 8~15자리가 가능합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String memNm;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    private String postCode;

    @Size(max = 50, message = "도로명 주소를 알맞게 입력해주세요.")
    private String address;

    @Size(max = 50, message = "상세 주소를 알맞게 입력해주세요.")
    private String deAddress;
    private String picture;


    public User toEntity() {
        return User.builder()
                .memId(this.getMemId())
                .email(this.getEmailAddress())
                .name(this.getMemNm())
                .phone(this.getTel())
                .postCode(this.getPostCode())
                .address(this.getAddress())
                .deAddress(this.getDeAddress())
                .picture(this.getPicture())
                .build();
    }
}
