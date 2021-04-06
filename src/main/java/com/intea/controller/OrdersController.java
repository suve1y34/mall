package com.intea.controller;

import com.intea.domain.*;
import com.intea.domain.dto.MembersDTO;
import com.intea.service.OrdersService;
import com.intea.util.SecurityUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrdersController extends UiUtils {
    private final OrdersService sService;
    private final SecurityUtils sUtils;

    @GetMapping("/member/order")
    public String getOrder() {
        return "shop/order/new-order";
    }

    //주문하기
    @RequestMapping(value = "/cart/order", method = RequestMethod.POST)
    public String order(HttpServletRequest req, OrdersEntity order, OrdersDetailEntity d_order) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
        String subNum = "";

        for(int i = 1; i <= 6; i ++) {
            subNum += (int)(Math.random() * 10);
        }

        String orderId = ymd + "_" + subNum;

        order.setI_order(orderId);
        order.setI_mem(i_mem);

        sService.insOrder(order);

        d_order.setI_order(orderId);
        sService.insOrder_details(d_order);

//        sService.cartAllDel(i_mem);

        return "redirect:/shop/orderList";
    }

    //특정 회원 주문 목록
    @RequestMapping(value = "/member/orderList", method = RequestMethod.GET)
    public String getOrderList(HttpServletRequest req, OrdersListDTO param, Model model) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        param.setI_mem(i_mem);

        List<OrdersListDTO> oList = sService.membersOrdersList(param);

        model.addAttribute("orderList", oList);

        return "shop/order/order-list";
    }

    //특정 주문 목록
    @GetMapping("/orderView")
    public String getOrderDetail(HttpServletRequest req, @RequestParam("n") String i_order,
                                 OrdersListDTO param, Model model) {
        MembersDTO loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        param.setI_mem(i_mem);
        param.setI_order(i_order);

        List<OrdersListDTO> orderView = sService.selOrdersDetail(param);

        model.addAttribute("orderView", orderView);

        return "shop/order/order-detail";
    }
}
