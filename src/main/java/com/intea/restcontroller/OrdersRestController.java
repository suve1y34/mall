package com.intea.restcontroller;

import com.intea.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Api(tags = "orders", description = "상품 주문")
@RestController
public class OrdersRestController {

    private OrdersService ordersService;

    @ApiOperation(value = "주문 상세")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrdersDetail(@PathVariable Long order_id) {
        return ResponseEntity.ok().body(ordersService.getOrderDetail(order_id));
    }

    @ApiOperation(value = "전체 주문 조회")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("user/{userId}/order/{page}")
    public ResponseEntity<?> getAllOrders(@PathVariable("userId") UUID user_id, @PathVariable("page") int page,
                                          @PageableDefault(size = 5, sort = "insert_time", direction = Sort.Direction.DESC)Pageable pageable) {
        return ResponseEntity.ok().body(ordersService.getAllOrder(user_id, page));
    }
}
