package com.intea.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String main() {

        return "/index";
    }

    //admin 메인화면
    @GetMapping("admin/main")
    public String admin_main() {
        return "user/admin/ad-index";
    }
}
