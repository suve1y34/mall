package com.intea.restcontroller;

import com.intea.domain.dto.MemberRequestDto;
import com.intea.domain.dto.UpdatePasswordRequestDto;
import com.intea.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j @AllArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    @ApiOperation(value = "회원 정보 조회")
    @GetMapping("/me/{id}")
    public ResponseEntity<?> getProfiles(@PathVariable UUID id) {

        return ResponseEntity.ok().body(userService.getProfiles(id));
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/me/{id}")
    public ResponseEntity<String> modifyProfiles(HttpServletRequest request, @PathVariable UUID id,
                                                 @RequestBody @Valid MemberRequestDto memberRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        request.getSession().setAttribute("user", userService.updateProfile(id, memberRequestDto));

        return ResponseEntity.ok("프로필 수정이 완료되었습니다!");
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/me/{id}")
    public ResponseEntity<String> deleteProfiles(@PathVariable UUID id) {

        userService.deleteProfile(id);

        return ResponseEntity.ok().body("탈퇴가 완료되었습니다.");
    }

    @ApiOperation(value = "회원 비밀번호 수정")
    @PutMapping("/me/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable UUID id,
                                                 @RequestBody @Valid UpdatePasswordRequestDto updatePasswordRequestDto,
                                                 BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        userService.updatePassword(id, updatePasswordRequestDto);

        return ResponseEntity.ok().body("비밀번호 수정이 완료되었습니다.");
    }
}
