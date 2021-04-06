package com.intea.service;

import com.intea.domain.OrdersDetailEntity;
import com.intea.domain.OrdersEntity;
import com.intea.domain.OrdersListDTO;
import com.intea.mapper.OrdersMapper;
import com.intea.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrdersService {

    private OrdersMapper sMapper;
    private SecurityUtils sUtils;

    public void insOrder(OrdersEntity param) {
        sMapper.insOrders(param);
    }

    public void insOrder_details(OrdersDetailEntity param) {
        sMapper.insOrders_details(param);
    }

    public List<OrdersListDTO> selOrdersDetail(OrdersListDTO param) {
        return sMapper.selOrdersDetail(param);
    }

    public List<OrdersListDTO> membersOrdersList(OrdersListDTO param) {
        return sMapper.membersOrdersList(param);
    }

}
