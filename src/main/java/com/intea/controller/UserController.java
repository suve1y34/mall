package com.intea.controller;

import com.intea.service.CategoryService;
import com.intea.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j @AllArgsConstructor
@Controller
public class UserController {
    private final CategoryService categoryService;
    private final UserService userService;

    //로그인 get
    @GetMapping("/login")
    public String getLogin(HttpServletRequest req, Model model) {
        String referrer = req.getHeader("Referer");
        req.getSession().setAttribute("prePage", referrer);
        model.addAttribute("catMapList", categoryService.getCategoryList());
        return "user/member/login";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) throws Exception {
        model.addAttribute("pageName", "profile");
        model.addAttribute("catMapList", categoryService.getCategoryList());

        return "user/member/myinfo";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/signout")
    public String logout() {
        return "/";
    }
}