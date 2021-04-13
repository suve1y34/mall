package com.intea.service;

import com.intea.domain.dto.MembersDTO;
import com.intea.domain.dto.UserResDTO;
import com.intea.domain.entity.User;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.NotExistUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String idChk(Map<String, Object> id) {
        Optional<User> user = userRepository.findById((String) id.get("id"));

        String result;

        if(user.isPresent()) {
            throw new NotExistUserException("이미 등록된 아이디입니다.");
        } else {
            result = "SUCCESS";
        }
        return result;
    }

    // 유저 프로필 조회
    public int getProfiles(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));

        return user.getSaving();
    }

    // 유저 프로필 수정
    public UserResDTO updateProfile(Long id, MembersDTO memDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User updatedUser = user.update(memDto);
        user = userRepository.save(updatedUser);

        return user.toResDTO(user);
    }

    // 유저 탈퇴
    public void deleteProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User disabledUser = user.delete();

        userRepository.save(disabledUser);
    }
}
