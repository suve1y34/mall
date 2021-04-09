package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.constant.Role;
import com.intea.domain.dto.MembersDTO;
import com.intea.domain.dto.UserResDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@Entity
public class User extends CommonEntity {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    @ColumnDefault("0")
    private Integer saving;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart> cartList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewList;


    public UserResDTO toResDTO(User user) {
        return UserResDTO.builder()
                .id(user.getId())
                .mem_id(user.getMem_id())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .postCode(user.getPostCode())
                .address(user.getAddress())
                .de_address(user.getDe_address())
                .picture(user.getPicture())
                .saving(user.getSaving())
                .build();
    }

    public UserResDTO.ReviewUserResDTO toReviewResDTO() {
        return UserResDTO.ReviewUserResDTO.builder()
                .user_id(mem_id)
                .build();
    }

    public User update(MembersDTO memDTO) {
        this.name =memDTO.getName();
        this.picture = memDTO.getPicture();
        this.email = memDTO.getEmail();
        this.postCode = memDTO.getPostCode();
        this.address = memDTO.getAddress();
        this.de_address = memDTO.getDe_address();

        return this;
    }

    public User delete() {
        this.delete_yn = 'Y';
        return this;
    }

    public User updPw(String pw) {
        this.pw = pw;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
