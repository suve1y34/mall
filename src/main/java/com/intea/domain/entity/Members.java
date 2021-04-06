package com.intea.domain.entity;

import com.intea.constant.Verify;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class Members extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    private Verify verify;

    private String delete_yn;
}
