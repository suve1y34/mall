package com.intea.restcontroller;

import com.intea.domain.dto.CategoryRequestDto;
import com.intea.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Api(tags = "category", description = "카테고리")
@RestController
public class CategoryRestController {
    private CategoryService categoryService;

    //전체 카테고리 조회
    @ApiOperation(value = "전체 카테고리 조회")
    @GetMapping("/categories")
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok().body(categoryService.getCategoryList());
    }
}
