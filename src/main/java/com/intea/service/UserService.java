package com.intea.service;

import com.intea.domain.dto.MemberRequestDto;
import com.intea.domain.dto.UpdatePasswordRequestDto;
import com.intea.domain.dto.UserResponseDto;
import com.intea.domain.entity.User;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.IdCheckException;
import com.intea.exception.NotExistUserException;
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
//    private final PasswordEncoder passwordEncoder;

    public boolean idChk(String id) {
        if(userRepository.existsByMemId(id)) {
            throw new IdCheckException("이미 등록된 아이디 입니다.");
        }
        return true;
    }

    // 유저 프로필 조회
    public int getProfiles(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));

        return user.getSaving();
    }

    // 유저 프로필 수정
    public UserResponseDto updateProfile(UUID id, MemberRequestDto memDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User updatedUser = user.update(memDto);
        user = userRepository.save(updatedUser);

        return user.toResDTO(user);
    }

    // 유저 비밀번호 변경
/*    public void updatePassword(UUID id, UpdatePasswordRequestDto passwordReqDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 유저입니다."));
        String beforePassword = user.getPassword();*/

/*        if(!isPasswordEquals(beforePassword, passwordRequestDto.getOldPassword())) {
            throw new UpdatePasswordException("기존 비밀번호를 잘못 입력하였습니다.");
        }

        if (isPasswordEquals(beforePassword, passwordRequestDto.getNewPassword())) {
            throw new UpdatePasswordException("기존 비밀번호와 바꾸려는 새 비밀번호가 일치합니다.");
        }*/

/*        User updatedUser = user.updPw(passwordEncoder.encode(passwordReqDTO.getNewPassword()));

        userRepository.save(updatedUser);
    }*/

    // 유저 탈퇴
    public void deleteProfile(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));;

        User disabledUser = user.delete();

        userRepository.save(disabledUser);
    }
}
