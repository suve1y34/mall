package com.intea.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CartController {

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", "cart");

        return "shop/order/cart";
    }
}
