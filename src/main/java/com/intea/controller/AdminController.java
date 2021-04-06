package com.intea.controller;

import com.intea.constant.Method;
import com.intea.domain.*;
import com.intea.domain.dto.MembersDTO;
import com.intea.service.AdminService;
import com.intea.service.ProductService;
import com.intea.util.FileUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminController extends UiUtils {
    private final AdminService aService;
    private final ProductService pService;
    private final FileUtils fileUtils;


    //상품등록화면 받아오기
    @GetMapping(value = "admin/pd-regmod")
    public void openPdRegmod(@ModelAttribute("param") ProductEntity param,
                             @RequestParam(value = "i_pd", required = false) Long i_product,
                             Model model, CategoryEntity ct) {
        List<CategoryEntity> category = pService.category();
        if(i_product == null) {
            model.addAttribute("product", new ProductEntity());
        } else {
            ProductEntity pd = pService.selProductDetail(i_product);
            if(pd == null || "Y".equals(pd.getDelete_yn())) {
                showMessageWithRedirect("없는 상품이거나 이미 삭제된 상품입니다.", "/pd/all.do", Method.GET, null, model);
            }
            model.addAttribute("product", pd);

            List<AttachEntity> fileList = aService.getAttachFileList(i_product);
            model.addAttribute("fileList", fileList);
        }
        model.addAttribute("category", JSONArray.fromObject(category));
    }

    //상품 등록.수정
/*    @RequestMapping(value = "/admin/product/regdo", method=RequestMethod.POST)
    public String regProduct(final ProductDTO param, final MultipartFile[] files, Model model) throws Exception {
        Map<String, Object> pagingParam = getPagingParams(param);

        try {
            boolean isReg = aService.registerProduct(param, files);
            if(isReg == false) {
                return showMessageWithRedirect("상품 등록에 실패하였습니다.", "/admin/main", Method.GET, pagingParam, model);
            } 
        } catch(DataAccessException e) {
            return showMessageWithRedirect("DB처리 과정에 문제가 발생하였습니다.", "/admin/main", Method.GET, pagingParam, model);
        } catch(Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/main", Method.GET, pagingParam, model);
        }

        return showMessageWithRedirect("상품 등록이 완료되었습니다.", "/admin/main", Method.GET, pagingParam, model);
    }*/

    //상품 삭제
    @PostMapping("admin/product/del")
    public String delProduct(@ModelAttribute("param") ProductEntity param, @RequestParam(value = "p", required = false)Long i_product, Model model) {
        if(i_product == null) {
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/admin/all-product", Method.GET, null, model);
        }

        Map<String, Object> pagingParam = getPagingParams(param);

        try {
            boolean isDel = aService.delProduct(i_product);
            if(isDel == false) {
                return showMessageWithRedirect("상품 삭제에 실패하였습니다.", "/admin/all-product", Method.GET, null, model);
            }
        } catch(DataAccessException e) {
            return showMessageWithRedirect("DB 처리 과정에 문제가 발생하였습니다.", "/admin/all-product", Method.GET, null, model);
        } catch(Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/all-product", Method.GET, null, model);
        }
        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/admin/all-product", Method.GET, null, model);
    }
    
    //주문 전체 리스트
    @GetMapping("admin/order/all")
    public String openOrderList(@ModelAttribute("param") OrdersEntity param, Model model) {
        List<OrdersEntity> odList = aService.selOrdersList(param);
        model.addAttribute("list", odList);

        return "order/order-list";
    }

    //회원 전체 리스트
    @GetMapping("admin/member/all")
    public String openMemberList(@ModelAttribute("param") MembersDTO param, Model model) {
        List<MembersDTO> memList = aService.selMemberList(param);
        model.addAttribute("list", memList);

        return "user/admin/ad-members";
    }

    //주문 상태 변경(보완 필)
    @PostMapping("/admin/order/upd")
    public String changeState(OrdersEntity param) {
        aService.updDelivery(param);

        OrdersEntity ov = aService.orderView(param);
        ProductEntity pd = new ProductEntity();
        OrdersDetailEntity od = new OrdersDetailEntity();
        aService.changeStock_sub(od);

        pd.setI_product(od.getI_product());
        pd.setStock(od.getStock());
        aService.changeStock(pd);

        return "redirect:/user/admin/order-detail?o=" + param.getI_order();
    }
}
