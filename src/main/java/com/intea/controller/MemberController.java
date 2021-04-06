package com.intea.controller;

import com.intea.domain.dto.MembersDTO;
import com.intea.service.MemberService;
import com.intea.util.SecurityUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController extends UiUtils {

    private final MemberService memberService;

    //회원가입 get
    @GetMapping("signup")
    public String getSignup() {
        return "user/member/join";
    }

    //로그인 get
    @GetMapping("signin")
    public String getLogin() {
        return "user/member/login";
    }

    // 로그아웃 결과 페이지
    @GetMapping("signout")
    public void logout() {
    }

    @PostMapping("user/join")
    public String signup(@ModelAttribute @Valid MembersDTO dto, RedirectAttributes ra) {
        memberService.signup(dto);

        ra.addFlashAttribute("success", "회원가입 완료! 환영합니다!");

        return "redirect:/user/signin";
    }
}