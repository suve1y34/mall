package com.intea.domain.dto;

import com.intea.domain.entity.Members;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Members members) {
        this.name = members.getName();
        this.email = members.getEmail();
        this.picture = members.getPicture();
    }
}

