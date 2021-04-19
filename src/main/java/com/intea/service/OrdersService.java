package com.intea.service;

import com.intea.constant.OrderStatus;
import com.intea.domain.dto.OrdersRequestDto;
import com.intea.domain.dto.OrdersResponseDto;
import com.intea.domain.dto.PagingDto;
import com.intea.domain.entity.Cart;
import com.intea.domain.entity.Orders;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.User;
import com.intea.domain.repository.CartRepository;
import com.intea.domain.repository.OrdersRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.NotExistCartException;
import com.intea.exception.NotExistOrdersException;
import com.intea.exception.NotExistUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class OrdersService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private OrdersRepository ordersRepository;
    private ProductRepository productRepository;

/*    public void makeOrder(OrdersRequestDto ordersRequestDto) {

        List<Long> cartIdList = ordersRequestDto.getCartIdList();

        Optional<Cart> cartOpt = cartRepository.findById(cartIdList.get(0));

        if (!cartOpt.isPresent()) {
            throw new NotExistCartException("존재하지 않는 장바구니 입니다.");
        }

        Cart cart = cartOpt.get();
        Long userId = cart.getUser().getId();

        Optional<User> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            throw new NotExistUserException("존재하지 않는 유저 입니다.");
        }

        Orders productOrder = ordersRepository.save(Orders.builder()
                .normalUser(userOpt.get())
                .orderNumber(ordersRequestDto.getOrderNum())
                .orderName(ordersRequestDto.getOrder_name())
                .amount(ordersRequestDto.getAmount())
                .deliveryMessage(ordersRequestDto.getMessage())
                .address(ordersRequestDto.getAddress())
                .orderStatus(OrderStatus.COMPLATE)
                .build());

        List<HashMap<String, Object>> productMapList = new ArrayList<>();

        for (Long cartId : cartIdList) {
            cartOpt = cartRepository.findById(cartId);

            if(cartOpt.isPresent()) {
                // 사용한 장바구니 비활성화
                cart = cartOpt.get();
                cart.setOrders(productOrder);

                HashMap<String, Object> productMap = new HashMap<>();
                productMap.put("product", cart.getProduct());
                productMap.put("productCount", cart.getCount());
                productMapList.add(productMap);

                cartRepository.save(cart);
            } else {
                throw new NotExistCartException("존재하지 않는 장바구니 입니다.");
            }
        }

        // 상품의 재고 수정
        for (HashMap<String, Object> productMap : productMapList) {
            Product product = (Product) productMap.get("product");
            Integer productCount = (Integer) productMap.get("productCount");

            product.setPurchase_cnt(product.getPurchase_cnt() + productCount);
            product.setTotal_cnt(product.getTotal_cnt() - productCount);
            productRepository.save(product);
        }
    }*/

    public OrdersResponseDto getOrderDetails(Long order_id) {

        Optional<Orders> orderOpt = ordersRepository.findById(order_id);

        if (!orderOpt.isPresent())
            throw new NotExistOrdersException("존재하지 않는 주문입니다.");

        return orderOpt.get().toResponseDTO();
    }

    public HashMap<String, Object> getAllOrder(UUID user_id, int page, Pageable pageable) {
        int realPage = (page == 0) ? 0 : (page - 1);
        pageable = PageRequest.of(realPage, 5);

        Page<Orders> productOrderPage = ordersRepository.findAllByUserIdOrderByInsertTimeDesc(user_id, pageable);

        if(productOrderPage.getTotalElements() > 0) {
            List<OrdersResponseDto> productOrderResponseDtoList = new ArrayList<>();

            for (Orders productOrder : productOrderPage) {
                productOrderResponseDtoList.add(productOrder.toResponseDTO());
            }

            PageImpl<OrdersResponseDto> productOrderResponseDTOs
                    = new PageImpl<>(productOrderResponseDtoList, pageable, productOrderPage.getTotalElements());

            PagingDto ordersPagingDto = new PagingDto();
            ordersPagingDto.setPagingInfo(productOrderResponseDTOs);

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("productOrderList", productOrderResponseDTOs);
            resultMap.put("productOrderPagingDto", ordersPagingDto);

            return resultMap;
        }

        return null;
    }
}