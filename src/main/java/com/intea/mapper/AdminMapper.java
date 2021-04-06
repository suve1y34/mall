package com.intea.mapper;

import com.intea.domain.dto.MembersDTO;
import com.intea.domain.OrdersDetailEntity;
import com.intea.domain.OrdersEntity;
import com.intea.domain.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    public int insProduct(ProductEntity param);
    public int updProduct(ProductEntity param);
    public int delProduct(Long param);

    public List<OrdersEntity> selOrdersList(OrdersEntity param);
    public int selOrdersTotalCnt(OrdersEntity param);
    public int updDelivery(OrdersEntity param);
    public int delOrder(OrdersEntity param);
    public OrdersEntity orderView(OrdersEntity param);

    public OrdersDetailEntity changeStock_sub(OrdersDetailEntity param);
    public int changeStock(ProductEntity param);

    public List<MembersDTO> selMemberList(MembersDTO param);
    public int selMemberTotalCnt(MembersDTO param);
}
