package com.intea.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class OrdersController {
    @GetMapping("/order")
    public String getOrders(Model model) {
        model.addAttribute("pageName", "order");
        return "shop/order/new-order";
    }
}
