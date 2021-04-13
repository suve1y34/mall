package com.intea.service;

import com.intea.constant.OrderStatus;
import com.intea.domain.dto.OrdersReqDTO;
import com.intea.domain.entity.Cart;
import com.intea.domain.entity.Orders;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.User;
import com.intea.domain.repository.CartRepository;
import com.intea.domain.repository.OrdersRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.NotExistCartException;
import com.intea.exception.NotExistUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class OrdersService {

    private CartRepository cartRepository;
    private UserRepository normalUserRepository;
    private OrdersRepository ordersRepository;
    private ProductRepository productRepository;

    public void makeOrder(OrdersReqDTO ordersReqDTO) {

        List<Long> cartIdList = ordersReqDTO.getCart_id_list();

        Optional<Cart> cartOpt = cartRepository.findById(cartIdList.get(0));

        if (!cartOpt.isPresent()) {
            throw new NotExistCartException("존재하지 않는 장바구니 입니다.");
        }

        Cart cart = cartOpt.get();
        Long userId = cart.getUser().getId();

        Optional<User> userOpt = normalUserRepository.findById(userId);

        if (!userOpt.isPresent()) {
            throw new NotExistUserException("존재하지 않는 유저 입니다.");
        }

        Orders productOrder = ordersRepository.save(Orders.builder()
                .normalUser(userOpt.get())
                .orderNumber(ordersReqDTO.getOrder_num())
                .orderName(ordersReqDTO.getOrder_name())
                .amount(ordersReqDTO.getAmount())
                .deliveryMessage(ordersReqDTO.getMessage())
                .address(ordersReqDTO.getAddress())
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
    }
}