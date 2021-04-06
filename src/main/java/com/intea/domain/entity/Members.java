package com.intea.domain.entity;

import com.intea.constant.Verify;
import com.intea.domain.dto.MembersDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
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

    @Enumerated(EnumType.STRING)
    private Verify verify;

    private Character delete_yn;

    public MembersDTO toResponseDTO(Members members) {
        return MembersDTO.builder()
                .id(members.getMem_id())
                .emailAddress(members.getEmail())
                .mem_nm(members.getNm())
                .tel(members.getPhone())
                .postCode(members.getPostCode())
                .address(members.getAddress())
                .de_address(members.getDe_address())
                .verify(members.getVerify())
                .delete_yn(members.getDelete_yn())
                .build();
    }
}
