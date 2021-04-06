package com.intea.domain;

import com.intea.constant.Verify;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberEntity extends CommonEntity {
    private Long i_mem;
    private String mem_id;
    private String email;
    private String pw;
    private String nm;
    private String phone;
    private String postCode;
    private String address;
    private String de_address;
    private String birth;
    private LocalDateTime last_login_time;
    private Verify verify;
    private String msg;
}
