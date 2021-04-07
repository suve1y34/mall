package com.intea.domain.entity;

import com.intea.constant.Role;
import com.intea.domain.dto.UserDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@Entity
public class User extends CommonEntity {

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

    public UserDTO toResponseDTO(User user) {
        return UserDTO.builder()
                .id(user.getMem_id())
                .emailAddress(user.getEmail())
                .mem_nm(user.getName())
                .tel(user.getPhone())
                .postCode(user.getPostCode())
                .address(user.getAddress())
                .de_address(user.getDe_address())
                .role(user.getRole())
                .delete_yn(user.getDelete_yn())
                .picture(user.getPicture())
                .build();
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
