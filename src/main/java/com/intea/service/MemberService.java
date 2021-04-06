package com.intea.service;

import com.intea.domain.dto.MembersDTO;
import com.intea.domain.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
