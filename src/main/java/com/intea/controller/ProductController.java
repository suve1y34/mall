package com.intea.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class ProductController {

    @GetMapping("/productList")
    public String productList(Model model) {
        model.addAttribute("pageName", "productList");

        return "shop/product/product-list";
    }

    @GetMapping("/product/detail")
    public String productDetail() {
        return "shop/product/product-list";
    }
}
