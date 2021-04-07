package com.intea.controller;

import com.intea.config.auth.LoginUser;
import com.intea.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model, @LoginUser SessionUser user) {

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/index";
    }

    //admin 메인화면
    @GetMapping("admin/main")
    public String admin_main() {
        return "user/admin/ad-index";
    }
}
