package com.intea.service;

import com.intea.constant.ProductStatus;
import com.intea.domain.dto.ProductReqDTO;
import com.intea.domain.dto.ProductResDTO;
import com.intea.domain.entity.Product;
import com.intea.domain.repository.ProductRepository;
import com.intea.exception.NotExistProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepo;

    //상품 조회
    public HashMap<String, Object> getProductList(String c_code, String sort_code) throws Exception {
        return null;
    }

    //상품 상세
    public ProductResDTO getProductDetail(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));

        int price = 0;
        if(product.getPrice() > 0) {
            price = product.getPrice();
        }

        return product.toResponseDTO(price);
    }

    //카테고리 코드로 상품 리스트 조회
    @Transactional
    public HashMap<String, Object> getProductListByCcode(int page, String large_ct, String small_ct) {
        int rPage = page - 1;
        PageRequest pageable = PageRequest.of(rPage, 10);

        Page<Product> productList = null;

        if(large_ct.equals("ALL") && small_ct.equals("ALL")) {
            productList = productRepo.findAll();
        } else if(!large_ct.equals("ALL") && small_ct.equals("ALL")) {
            productList = productRepo.findAllByLarge_ct(large_ct, pageable);
        } else if(!large_ct.equals("ALL")) {
            productList = productRepo.findBySmall_ct(small_ct, pageable);
        }

        if(productList.getTotalElements() > 0) {
            return null;
        }
        return null;
    }

    public String addProduct(ProductReqDTO productReqDTO) {
        productRepo.save(Product.builder()
                .p_nm(productReqDTO.getP_nm())
                .price(productReqDTO.getPrice())
                .title_img(productReqDTO.getTitle_img())
                .large_ct(productReqDTO.getLarge_ct())
                .small_ct(productReqDTO.getSmall_ct())
                .product_status(ProductStatus.SALE)
                .cartList(new ArrayList<>())
                .productImgList(new ArrayList<>())
                .reviewList(new ArrayList<>())
                .purchase_cnt(0)
                .total_cnt(productReqDTO.getTotal_cnt())
                .build());

        return "상품이 등록 되었습니다.";
    }
}
