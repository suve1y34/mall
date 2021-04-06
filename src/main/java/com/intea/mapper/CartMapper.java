package com.intea.mapper;

import com.intea.domain.CartEntity;
import com.intea.domain.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    public void cartAllDel(Long i_mem);

    public int insCart(CartEntity param);
    public int updCart(CartEntity param);
    public void delCart(CartEntity param);
    public List<ShopDTO> selCartList(Long i_mem);
}
