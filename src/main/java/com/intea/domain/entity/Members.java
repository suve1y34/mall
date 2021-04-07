package com.intea.domain.entity;

import com.intea.constant.Role;
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
    private String name;
    private String phone;
    private String postCode;
    private String address;
    private String de_address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Character delete_yn;

    private String picture;

    public MembersDTO toResponseDTO(Members members) {
        return MembersDTO.builder()
                .id(members.getMem_id())
                .emailAddress(members.getEmail())
                .mem_nm(members.getName())
                .tel(members.getPhone())
                .postCode(members.getPostCode())
                .address(members.getAddress())
                .de_address(members.getDe_address())
                .role(members.getRole())
                .delete_yn(members.getDelete_yn())
                .picture(members.getPicture())
                .build();
    }

    public Members update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
