package com.intea.restcontroller;

import com.intea.domain.dto.OrdersReqDTO;
import com.intea.service.OrdersService;
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
public class OrdersRestController {

    private OrdersService ordersService;

    @PostMapping("/order")
    public ResponseEntity<?> newOrder(@RequestBody @Valid OrdersReqDTO ordersReqDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        }

        ordersService.makeOrder(ordersReqDTO);

        return ResponseEntity.ok().body("주문이 완료되었습니다.");
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long order_id) {
        return ResponseEntity.ok().body(ordersService.getOrderDetails(order_id));
    }

    @GetMapping("user/{userId}/{order/{page}")
    public ResponseEntity<?> getAllOrders(@PathVariable("userId") Long user_id, @PathVariable("page") int page,
                                          @PageableDefault(size = 5, sort = "insert_time", direction = Sort.Direction.DESC)Pageable pageable) {
        return ResponseEntity.ok().body(ordersService.getAllOrder(user_id, page, pageable));
    }
}
