package com.intea.service;

import com.intea.domain.dto.OrdersResponseDto;
import com.intea.domain.dto.PagingDto;
import com.intea.domain.entity.Orders;
import com.intea.domain.repository.OrdersRepository;
import com.intea.exception.NotExistOrdersException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class OrdersService {

    private OrdersRepository ordersRepository;

    public OrdersResponseDto getOrderDetail(Long order_id) {

        Optional<Orders> orderOpt = ordersRepository.findById(order_id);

        if (!orderOpt.isPresent())
            throw new NotExistOrdersException("존재하지 않는 주문입니다.");

        return orderOpt.get().toResponseDto();
    }

    public HashMap<String, Object> getAllOrder(UUID user_id, int page) {
        int realPage = (page == 0) ? 0 : (page - 1);
        PageRequest pageable = PageRequest.of(realPage, 5);

        Page<Orders> productOrderPage = ordersRepository.findAllByUserIdOrderByInsertTimeDesc(user_id, pageable);

        if(productOrderPage.getTotalElements() > 0) {
            List<OrdersResponseDto> productOrderResponseDtoList = new ArrayList<>();

            for (Orders productOrder : productOrderPage) {
                productOrderResponseDtoList.add(productOrder.toResponseDto());
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