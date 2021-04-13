package com.intea.controller;

import com.intea.service.CartService;
import com.intea.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@AllArgsConstructor
@Controller
public class ReviewController {
    private ProductService productService;
    private CartService cartService;

    @GetMapping("/review")
    public String getReview(@RequestParam Long user_id, @RequestParam Long product_id, Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user_id);
        map.put("productId", product_id);

        return "board/review/review-list";
    }
}
