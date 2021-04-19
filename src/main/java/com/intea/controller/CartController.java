package com.intea.controller;

import com.intea.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {
    private final CategoryService categoryService;

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("pageName", "cart");
//        model.addAttribute("catMapList", categoryService.getCategoryList());

        return "shop/order/cart";
    }
}
