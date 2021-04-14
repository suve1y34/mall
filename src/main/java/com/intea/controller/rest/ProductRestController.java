package com.intea.controller.rest;

import com.intea.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.TypeCache;
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

    @GetMapping("/productList/{page}/ct/{c_code}")
    public ResponseEntity<?> getProductList(@PathVariable("page") int page, @PathVariable("c_code") String c_code,
                                            @PageableDefault(size = 9, sort = "insert_time", direction = Sort.Direction.DESC)Pageable pageable) {
        return ResponseEntity.ok().body(productService.getProductListByCcode(c_code, pageable));
    }
}
