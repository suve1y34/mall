package com.intea.controller;

import com.intea.service.CartService;
import com.intea.service.CategoryService;
import com.intea.service.ProductService;
import com.intea.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final CartService cartService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;

    @GetMapping("/review")
    public String getReview(@RequestParam Long user_id, @RequestParam Long product_id, Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user_id);
        map.put("productId", product_id);

        model.addAttribute("chkReviewAuth", cartService.chkReviewAuthority(map));
        model.addAttribute("productNm", reviewService.insReview(product_id));
//        model.addAttribute("catMapList", categoryService.getCategoryList());

        return "board/review/review-list";
    }
}
