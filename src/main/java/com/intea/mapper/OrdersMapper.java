package com.intea.mapper;

import com.intea.domain.OrdersDetailEntity;
import com.intea.domain.OrdersEntity;
import com.intea.domain.OrdersListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {

    public void insOrders(OrdersEntity param);
    public void insOrders_details(OrdersDetailEntity param);
    public List<OrdersListDTO> selOrdersDetail(OrdersListDTO param);
    public List<OrdersListDTO> membersOrdersList(OrdersListDTO param);
}
