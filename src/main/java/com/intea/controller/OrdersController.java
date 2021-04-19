package com.intea.controller;

import com.intea.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class OrdersController {
    private final CategoryService categoryService;

    @GetMapping("/order")
    public String getOrders(Model model) {
        model.addAttribute("pageName", "order");
        model.addAttribute("catMapList", categoryService.getCategoryList());

        return "shop/order/new-order";
    }
}
