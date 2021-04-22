package com.intea.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class AdminController {


    @GetMapping("admin/product")
    public String getProductList() {
        return "user/admin/pd-list";
    }


}
