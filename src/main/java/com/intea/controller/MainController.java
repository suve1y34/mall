package com.intea.controller;

import com.intea.domain.ShopDTO;
import com.intea.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final ProductService productService;

    @GetMapping("/")
    public String main(Model model, ShopDTO param) {
        model.addAttribute("categoryList", productService.category());
        model.addAttribute("newProductList", productService.selNewProduct(param));

        return "/index";
    }
}
