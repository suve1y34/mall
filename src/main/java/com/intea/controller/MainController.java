package com.intea.controller;

import com.intea.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class MainController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String main(ModelMap model /*, @LoginUser SessionUser user*/) {

        model.addAttribute("pageName", "main");
        model.addAttribute("catMapList", categoryService.getCategoryList());
        return "index";
    }

    //admin 메인화면
    @GetMapping("admin/main")
    public String admin_main() {
        return "user/admin/ad-index";
    }
}
