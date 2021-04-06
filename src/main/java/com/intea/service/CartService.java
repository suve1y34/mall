package com.intea.service;

import com.intea.constant.Const;
import com.intea.domain.CartEntity;
import com.intea.domain.dto.MembersDTO;
import com.intea.domain.ShopDTO;
import com.intea.mapper.CartMapper;
import com.intea.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@Service
public class CartService {
    private CartMapper cartMapper;
    private SecurityUtils sUtils;

    public void cartAllDel(Long i_mem) {
        cartMapper.cartAllDel(i_mem);
    }

    public int insCart(CartEntity param, HttpServletRequest req) {
        MembersDTO loginMem = sUtils.getLoginMem(req);

        int result = cartMapper.insCart(param);

        Long i_product = param.getI_product();
        if(loginMem == null) {
            result = Const.FAIL;
        }else if(i_product == Const.ZERO) {
            result = Const.FAIL;
        } else {
            result = Const.SUCCESS;
        }

        return result;
    }

    public void updCart(CartEntity param) {
        cartMapper.updCart(param);
    }

    public void delCart(CartEntity param) {
        cartMapper.updCart(param);
    }

    public List<ShopDTO> selCartList(Long i_mem) {
        return cartMapper.selCartList(i_mem);
    }
}
