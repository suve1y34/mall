package com.intea.service;

import com.intea.domain.dto.ProductResDTO;
import com.intea.domain.entity.Product;
import com.intea.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepo;

    public HashMap<String, Object> getProductList(String c_code, String sort_code) throws Exception {
        return null;
    }

    public ProductResDTO getProductDetail(Long id) {
        Product product = productRepo.findById(id);
    }
}
