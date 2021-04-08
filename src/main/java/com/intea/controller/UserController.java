package com.intea.controller;

import com.intea.service.UserService;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class UserController extends UiUtils {

    private final UserService userService;

    //로그인 get
    @GetMapping("signin")
    public String getLogin(HttpServletRequest req, Model model) {

        return "user/member/login";
    }

    // 로그아웃 결과 페이지
    @GetMapping("signout")
    public void logout() {
    }
}