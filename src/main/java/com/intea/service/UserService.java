package com.intea.service;

import com.intea.domain.dto.MemberRequestDto;
import com.intea.domain.dto.UpdatePasswordRequestDto;
import com.intea.domain.dto.UserResponseDto;
import com.intea.domain.entity.User;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.IdCheckException;
import com.intea.exception.NotExistUserException;
import com.intea.exception.UpdatePasswordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 프로필 조회
    public int getProfiles(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));

        return user.getSaving();
    }

    // 유저 프로필 수정
    public UserResponseDto updateProfile(UUID id, MemberRequestDto memberRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User updatedUser = user.update(memberRequestDto);
        user = userRepository.save(updatedUser);

        return user.toResponseDto(user);
    }

    // 유저 비밀번호 변경
    public void updatePassword(UUID id, UpdatePasswordRequestDto passwordRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 유저입니다."));
        String beforePassword = user.getPassword();

        if(!isPasswordEquals(beforePassword, passwordRequestDto.getOldPassword())) {
            throw new UpdatePasswordException("기존 비밀번호를 잘못 입력하였습니다.");
        }

        if (isPasswordEquals(beforePassword, passwordRequestDto.getNewPassword())) {
            throw new UpdatePasswordException("기존 비밀번호로는 바꾸실 수 없습니다.");
        }

        User updatedUser = user.updPw(passwordEncoder.encode(passwordRequestDto.getNewPassword()));

        userRepository.save(updatedUser);
    }

    private boolean isPasswordEquals(String newPw, String oldPw) {
        return passwordEncoder.matches(newPw, oldPw);
    }

    // 유저 탈퇴
    public void deleteProfile(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User disabledUser = user.delete();

        userRepository.save(disabledUser);
    }
}
