package com.intea.restcontroller;

import com.intea.domain.dto.CartReqDTO;
import com.intea.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Api(tags = "cart", description = "장바구니")
@AllArgsConstructor
@RestController
public class CartRestController {
    private CartService cartService;

    //카트목록 조회
    @ApiOperation(value = "장바구니 조회")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/user/{userId}/cart/{page}")
    public ResponseEntity<?> getCartList(@PathVariable("userId") UUID user_id, @PathVariable("page") int page,
                                         @PageableDefault(size = 5, sort = "insert_time", direction = Sort.Direction.DESC)Pageable pageable) {
        return ResponseEntity.ok().body(cartService.getCartList(user_id, page, pageable));
    }

    //카트 추가
    @ApiOperation(value = "장바구니 추가")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/addCart")
    public ResponseEntity<?> insCart(@RequestBody @Valid CartReqDTO cartReqDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        }

        cartService.getCart(cartReqDTO);

        return ResponseEntity.ok().body("장바구니 추가 완료");
    }

    //카트 삭제
    @ApiOperation(value = "장바구니 삭제")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> delCart(@PathVariable Long id) {
        cartService.delCart(id);
        return ResponseEntity.ok().body("삭제가 완료 되었습니다.");
    }
}
