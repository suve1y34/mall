package com.intea.controller.rest;

import com.intea.domain.dto.MembersDTO;
import com.intea.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Slf4j @AllArgsConstructor
@RestController
public class UserRestController {
    private UserService userService;

    @GetMapping("/duplchk")
    public String duplicateCheck(@RequestParam Map<String, Object> id) {
        return userService.idChk(id);
    }

    @PutMapping("/my/{id}")
    public ResponseEntity<String> modifyProfile(HttpServletRequest req, @PathVariable Long id,
                                                @RequestBody @Valid MembersDTO membersDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        }

        req.getSession().setAttribute("user", userService.updateProfile(id, membersDTO));

        return ResponseEntity.ok("프로필 수정이 완료되었습니다.");
    }

    @DeleteMapping("/my/{id}")
    public ResponseEntity<String> delProfile(@PathVariable Long id) {
        userService.deleteProfile(id);

        return ResponseEntity.ok().body("탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
    }
}
