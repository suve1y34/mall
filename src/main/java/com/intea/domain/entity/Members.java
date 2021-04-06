package com.intea.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String verify;
    private String delete_yn;
}
