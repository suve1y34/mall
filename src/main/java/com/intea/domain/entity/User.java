package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.common.CommonEntity;
import com.intea.domain.dto.MemberRequestDto;
import com.intea.domain.enums.Role;
import com.intea.domain.dto.UserResponseDto;
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

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String memId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String postCode;
    private String address;
    private String deAddress;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Character deleteYn;

    private String picture;

    private String authorities;

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


    public UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .memId(user.getMemId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .postCode(user.getPostCode())
                .address(user.getAddress())
                .deAddress(user.getDeAddress())
                .picture(user.getPicture())
                .saving(user.getSaving())
                .build();
    }

    public UserResponseDto.ReviewUserResponseDto toReviewResponseDto() {
        return UserResponseDto.ReviewUserResponseDto.builder()
                .memId(memId)
                .build();
    }

    public User update(MemberRequestDto memberRequestDto) {
        this.phone = memberRequestDto.getPhone();
        this.postCode = memberRequestDto.getPostCode();
        this.address = memberRequestDto.getAddress();
        this.deAddress = memberRequestDto.getDeAddress();
        this.picture = memberRequestDto.getPicture();

        return this;
    }

    public User delete() {
        this.deleteYn = 'Y';
        return this;
    }

    public User updPw(String pw) {
        this.password = pw;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
