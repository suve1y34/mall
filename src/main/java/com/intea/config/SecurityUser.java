package com.intea.config;

import com.intea.domain.entity.Members;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;
    private Members members;

    public SecurityUser(Members members) {
        super(members.getMem_id(), "{noop}" + members.getPw(),
                AuthorityUtils.createAuthorityList(members.getVerify().toString()));
    }

    public Members getMembers() {
        return members;
    }
}
