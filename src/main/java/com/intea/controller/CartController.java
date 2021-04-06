package com.intea.controller;

import com.intea.constant.Const;
import com.intea.domain.CartEntity;
import com.intea.domain.dto.MembersDTO;
import com.intea.domain.ShopDTO;
import com.intea.service.CartService;
import com.intea.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final SecurityUtils sUtils;

    //카트담기
    @ResponseBody
    @RequestMapping(value = "/cart/{i_mem}/cart", method = RequestMethod.POST)
    public void addCart(@PathVariable("i_mem") Long i_mem, CartEntity param, HttpServletRequest req, Model model) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        param.setI_mem(loginMem.getI_mem());

        List<CartEntity> list = new ArrayList<>();

        boolean added = false;

        for(int i=0; i<list.size(); i++) {
            CartEntity cart = list.get(i);

            if(cart.getI_product() == param.getI_product()) {
                cartService.updCart(param);
                added = true;
                break;
            }

            if(!added) {
                cartService.insCart(param, req);
            }
        }
    }

    //카트화면
    @GetMapping("/order/cart")
    public void getCartList(HttpServletRequest req, Model model) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        List<ShopDTO> cartList = cartService.selCartList(i_mem);

        model.addAttribute("cartList", cartList);
    }

    //카드 삭제
    @PostMapping("/cart/delete")
    public int delCart(@RequestParam(value = "chbox[]")List<String> chArr, CartEntity param, HttpServletRequest req) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        int result = 0;
        Long i_cart;

        if(loginMem != null) {
            param.setI_mem(i_mem);

            for(String i : chArr) {
                i_cart = Long.parseLong(i);
                param.setI_cart(i_cart);
            }
            result = Const.SUCCESS;
        }
        return result;
    }
}
