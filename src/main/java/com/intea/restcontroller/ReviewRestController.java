package com.intea.restcontroller;

import com.intea.domain.dto.ReviewRequestDto;
import com.intea.service.CartService;
import com.intea.service.ReviewService;
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
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
@Api(tags = "review", description = "리뷰")
@RestController
public class ReviewRestController {

    private ReviewService reviewService;
    private CartService cartService;

    // 리뷰를 작성할 수 있는 회원인지
    @ApiOperation(value = "리뷰 작성 권한 확인")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/review/authority")
    public ResponseEntity<?> checkReviewAuthority(@RequestParam HashMap<String, Object> paramMap) {

        return ResponseEntity.ok().body(cartService.chkReviewAuthority(paramMap));
    }

    // 리뷰 추가
    @ApiOperation(value = "리뷰 작성")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/review")
    public ResponseEntity<?> makeReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        reviewService.makeReview(reviewRequestDto);

        return ResponseEntity.ok().body("리뷰가 추가되었습니다.");
    }

    // 리뷰리스트 조회
    @ApiOperation(value = "리뷰 조회")
    @GetMapping("/product/{productId}/review")
    public ResponseEntity<?> getReviewList(@PathVariable("productId") Long product_id,
                                           @RequestParam("page") int page) {

        return ResponseEntity.ok().body(reviewService.getReviewList(product_id, page));
    }

    // 리뷰 상세 조회
    @ApiOperation(value = "리뷰 상세")
    @GetMapping("/review/{id}")
    public ResponseEntity<?> getReviewDetail(@PathVariable Long id) {

        return ResponseEntity.ok().body(reviewService.getReviewDetail(id));
    }
}
