package com.intea.restcontroller;

import com.intea.domain.dto.CartReqDTO;
import com.intea.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
public class CartRestController {
    private CartService cartService;

    //카트목록 조회
    @GetMapping("/user/{userId}/cart/{page}")
    public ResponseEntity<?> getCartList(@PathVariable("userId") Long user_id, @PathVariable("page") int page,
                                         @PageableDefault(size = 5, sort = "insert_time", direction = Sort.Direction.DESC)Pageable pageable) {
        return ResponseEntity.ok().body(cartService.getCartList(user_id, page, pageable));
    }

    //카트 추가
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
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> delCart(@PathVariable Long id) {
        cartService.delCart(id);
        return ResponseEntity.ok().body("삭제가 완료 되었습니다.");
    }
}
