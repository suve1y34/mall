package com.intea.restcontroller;

import com.intea.common.Const;
import com.intea.domain.dto.ProductRequestDto;
import com.intea.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "product", description = "상품")
@Slf4j @AllArgsConstructor
@RestController
public class ProductRestController {
    private final ProductService productService;

    @ApiOperation(value = "상품 조회")
    @GetMapping("/product")
    public ResponseEntity<?> getProductList(@RequestParam(value = "catCd", required = false) String c_code,
                                            @RequestParam(value = "page", required = false) int page,
                                            @RequestParam(value = "sortCd", required = false) String sort_code) throws Exception {

        return ResponseEntity.ok().body(productService.getProductList(c_code, sort_code, page));
    }

    @ApiOperation(value = "상품 상세")
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {

        return ResponseEntity.ok().body(productService.getProductDetails(id));
    }

    // 상품 리스트 조회 (관리자 권한)
    @ApiOperation(value = "상품 전체 조회 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/productList/{page}")
    public ResponseEntity<?> getAdminProductList(@PathVariable int page) {

        return ResponseEntity.ok().body(productService.getAdminProductList(page));
    }

    // 1차 카테고리 코드와 2차 카테고리 코드로 상품 리스트 조회하기 (관리자 권한)
    @ApiOperation(value = "카테고리를 통한 상품 조회 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/productList/{page}/firstCategory/{firstCatCd}/secondCategory/{secondCatCd}")
    public ResponseEntity<?> getProductListByCatCd(@PathVariable("page") int page, @PathVariable("firstCatCd") String firstCatCd,
                                                   @PathVariable("secondCatCd") String secondCatCd) {

        return ResponseEntity.ok().body(productService.getProductListByCatCd(page, firstCatCd, secondCatCd));
    }

    // 상품 타이틀 이미지 업로드 (관리자 권한)
    @ApiOperation(value = "상품 타이틀 이미지 업로드 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/titleImage")
    public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok().body(productService.uploadProductImage(file, Const.PRODUCT_UPLOAD_IMAGE));
    }

    // 상품 추가 (관리자 권한)
    @ApiOperation(value = "상품 추가 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(productService.addProduct(productRequestDto));
    }

    // 상품 상세 (관리자 권한)
    @ApiOperation(value = "상품 상세 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/product/{id}")
    public ResponseEntity<?> getAdminProductDetails(@PathVariable Long id) {

        return ResponseEntity.ok().body(productService.getAdminProductDetails(id));
    }

    // 상품 수정 (관리자 권한)
    @ApiOperation(value = "상품 수정 (관리자 권한)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto.UpdateResponseDto updateReqDTO,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(productService.updateProduct(id, updateReqDTO));
    }

}
