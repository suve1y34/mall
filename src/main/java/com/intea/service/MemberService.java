package com.intea.service;

import com.intea.config.SecurityUser;
import com.intea.constant.Verify;
import com.intea.domain.dto.MembersDTO;
import com.intea.domain.entity.Members;
import com.intea.domain.repository.MembersRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MembersRepository membersRepo;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void signup(MembersDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        membersRepo.save(dto.toEntity());
    }
}
