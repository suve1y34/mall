package com.intea.service;

import com.intea.domain.dto.UserDTO;
import com.intea.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final UserRepository membersRepo;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void signup(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        membersRepo.save(dto.toEntity());
    }
}
