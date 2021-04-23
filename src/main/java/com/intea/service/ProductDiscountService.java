package com.intea.service;

import com.intea.domain.dto.ProductDisPriceRequestDto;
import com.intea.domain.dto.ProductDisPriceResponseDto;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.ProductDisPrice;
import com.intea.domain.repository.ProductDisPriceRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.exception.NotExistProductDisPriceException;
import com.intea.exception.NotExistProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDiscountService {

    private final ProductRepository productRepository;
    private final ProductDisPriceRepository productDisPriceRepository;

    @Transactional
    public HashMap<String, Object> getDisCountList(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(!productOptional.isPresent()) {
            throw new NotExistProductException("존재하지 않는 상품입니다.");
        }

        Product product = productOptional.get();

        List<ProductDisPrice> productDisPriceList = productDisPriceRepository.findByProductId(id);

        int listSize = productDisPriceList.size();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("productNm", product.getProductNm());

        if(listSize > 0) {
            List<ProductDisPrice> applyDiscountProductList = productDisPriceList.stream()
                    .filter(productDisPrice -> LocalDateTime.now().isAfter(productDisPrice.getStartDate())
                    && LocalDateTime.now().isBefore(productDisPrice.getEndDate()))
                    .sorted().limit(1).collect(Collectors.toList());

            Long applyDisCountProductId = 0L;

            if(applyDiscountProductList.size() > 0) {
                ProductDisPrice applyDiscountProduct = applyDiscountProductList.get(0);
                ProductDisPriceResponseDto res = applyDiscountProduct.toResponseDto();

                applyDisCountProductId = res.getId();

                resultMap.put("applyDisCountProduct", res);
            }

            Long finalApply = applyDisCountProductId;

            List<ProductDisPriceResponseDto> noApply
                    = productDisPriceList.stream().map(ProductDisPrice::toResponseDto)
                    .filter(productDisPriceResponseDto -> !productDisPriceResponseDto.getId().equals(finalApply))
                    .collect(Collectors.toList());

            resultMap.put("NoApplyDiscountProductList", noApply);
        }

        return resultMap;
    }

    // 할인 리스트 추가
    public String addProductDiscount(ProductDisPriceRequestDto productDisPriceRequestDto) {
        Optional<Product> productOpt = productRepository.findById(productDisPriceRequestDto.getProductId());

        if (!productOpt.isPresent())
            throw new NotExistProductException("존재하지 않는 상품 입니다.");

        productDisPriceRepository.save(ProductDisPrice.builder()
                .product(productOpt.get())
                .startDate(productDisPriceRequestDto.getStartDate().minusHours(15))
                .endDate(productDisPriceRequestDto.getEndDate().plusHours(8).plusMinutes(59).plusSeconds(59))
                .rateYn('Y')
                .disPrice(productDisPriceRequestDto.getDisPrice())
                .build());

        return "할인 리스트가 추가되었습니다.";
    }

    // 할인 리스트 삭제
    public String deleteProductDiscount(Long id) {
        Optional<ProductDisPrice> productDisPrcOpt = productDisPriceRepository.findById(id);

        if (!productDisPrcOpt.isPresent())
            throw new NotExistProductDisPriceException("존재하지 않는 할인 리스트 입니다.");

        productDisPriceRepository.delete(productDisPrcOpt.get());

        return "할인 리스트가 삭제되었습니다.";
    }
}
