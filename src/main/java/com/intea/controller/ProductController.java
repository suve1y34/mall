package com.intea.controller;

import com.intea.constant.Method;
import com.intea.domain.*;
import com.intea.service.AdminService;
import com.intea.service.ProductService;
import com.intea.util.SecurityUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ProductController extends UiUtils {
    private final ProductService pService;
    private final AdminService aService;
    private final SecurityUtils sUtils;


    //상품 상세페이지
    @GetMapping(value = "/product/view")
    public String openProductDetail(@RequestParam(value = "p",required = false)Long i_product, Model model) {
        if(i_product == null) {
            return "redirect:/product/list";
        }

        ShopDTO product = pService.selProductDetail(i_product);
        if(product == null || "Y".equals(product.getDelete_yn())) {
            return showMessageWithRedirect("없는 상품이거나 이미 삭제된 상품입니다.", "/product/list", Method.GET, null, model);
        }

        model.addAttribute("product", product);

        List<AttachEntity> fileList = aService.getAttachFileList(i_product); // 추가된 로직
        model.addAttribute("fileList", fileList);

        return "shop/product/product-detail";
    }

    @GetMapping("/product/ct/{ct}")
    public Map<String, Object> getProductList(@PathVariable("ct") String c_code, CategoryEntity ct, ShopDTO param) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("result", pService.getProductListByCt(c_code, param));

        return returnMap;
    }

    //상품 전체 리스트
/*    @GetMapping("product/list")
    public String openAllPdList(@ModelAttribute("param") ShopDTO param, Model model) {
        List<ShopDTO> pdList = pService.getProductList(param);
        model.addAttribute("list", pdList);

        return "shop/product/product-list";
    }*/
}
