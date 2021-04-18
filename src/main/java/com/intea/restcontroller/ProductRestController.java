package com.intea.restcontroller;

import com.intea.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j @AllArgsConstructor
@RestController
public class ProductRestController {
    private ProductService productService;

    @GetMapping("/productList/{page}/ct/{c_code}/sc/{sort_code}")
    public ResponseEntity<?> getProductList(@PathVariable("page") int page, @PathVariable("c_code") String c_code,
                                            @PathVariable("sc")String sort_code) {
//        return ResponseEntity.ok().body(productService.getProductListByKeyword(c_code, pageable, String sort_code));
        return null;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductDetail(id));
    }
}