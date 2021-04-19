package com.intea.controller;

import com.intea.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@AllArgsConstructor
@Controller
public class ProductController {
    private final CategoryService categoryService;

    @GetMapping("/productList")
    public String productList(Model model) {
        model.addAttribute("pageName", "productList");
//        model.addAttribute("catMapList", categoryService.getCategoryList());

        return "shop/product/product-list";
    }

    @GetMapping("/product/detail")
    public String productDetail(Model model, @RequestParam("productId") Long id) {
//        model.addAttribute("catMapList", categoryService.getCategoryList());
        model.addAttribute("productId", id);
        return "shop/product/product-list";
    }
}
