package com.intea.service;

import com.amazonaws.services.s3.AmazonS3;
import com.intea.constant.AWSS3Utils;
import com.intea.constant.ProductStatus;
import com.intea.constant.UploadFileUtils;
import com.intea.domain.dto.PagingDto;
import com.intea.domain.dto.ProductRequestDto;
import com.intea.domain.dto.ProductResponseDto;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.ProductDisPrice;
import com.intea.domain.repository.ProductRepository;
import com.intea.exception.NoValidProductSortException;
import com.intea.exception.NotExistProductException;
import com.intea.exception.ProductListException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final AWSS3Utils awss3Utils;
    private final ProductRepository productRepository;
//    private final ZSetOperations<String, Object> zSetOperations;

    // 전체 상품 혹은 카테고리로 상품 조회
    public HashMap<String, Object> getProductList(String catCd, String sortCd, int page) throws Exception {
        int realPage = (page == 0) ? 0 : page - 1;
        Pageable pageable = PageRequest.of(realPage, 9, Sort.by("insertTime")/*new Sort(Sort.Direction.DESC, "insertTime")*/);
        // 카테고리로 조회
        if(isNull(sortCd)) {
            return getResult(getProductsByCategory(catCd, pageable), pageable);
        }

        // 정렬 기준으로 조회
        if(nonNull(sortCd) && nonNull(catCd)) {
            return getResult(getProductsByCatCdAndSortCd(catCd, sortCd, realPage), pageable);
        }

        throw new NoValidProductSortException("유효하지 않은 상품 조회 요청 파라미터 입니다.");
    }

    // 상품 상세
    public ProductResponseDto getProductDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NotExistProductException("존재하지 않는 상품입니다."));

        int disPrice = 0;
        if (product.getProductDisPrcList().size() > 0) {
            disPrice = getDisPrice(product);
        }

        return product.toResponseDTO(disPrice);
    }

    /**
     * 인기 상위 10개의 상품 조회
     */
    public List<ProductResponseDto.MainProductResponseDto> getBestProductList() {
        // 레디스 캐시(메모리) I/O
//        Set<Object> result = zSetOperations.reverseRange(BEST10_PRODUCT_LIST_KEY, 0, 9);

/*        if(isNull(result)) {
            return null;
        } else {
            return result.stream().map(el -> (ProductResDTO.MainProductResDTO) el).collect(Collectors.toList());
        }*/
        return null;
    }

    /**
     * 최신 상위 8개 상품 조회
     */
    public List<ProductResponseDto.MainProductResponseDto> getNewProductList() {
/*        // 레디스 캐시(메모리) I/O
        Set<Object> result = zSetOperations.reverseRange(NEW8_PRODUCT_LIST_KEY, 0, 7);

        if(isNull(result)) {
            return null;
        } else {
            return result.stream().map(el -> (ProductResponseDto.MainProductResponseDto) el).collect(Collectors.toList());
        }*/
        return null;
    }

    public HashMap<String, Object> getAdminProductList(int page) {
        int realPage = page - 1;
        PageRequest pageable = PageRequest.of(realPage, 10, Sort.by("insertTime")/*new Sort(Sort.Direction.DESC, "insertTime")*/);

        Page<Product> productPage = productRepository.findAll(pageable);

        if (productPage.getTotalElements() > 0) {
            return getAdminProductListMap(productPage, pageable);
        }

        return null;
    }

    // 1차 카테고리 코드와 2차 카테고리 코드로 상품 리스트 조회하기
    @Transactional
    public HashMap<String, Object> getProductListByCatCd(int page, String firstCatCd, String secondCatCd) {
        int realPage = page - 1;
        PageRequest pageable = PageRequest.of(realPage, 10, Sort.by("insertTime")/*new Sort(Sort.Direction.DESC, "insertTime")*/);

        Page<Product> productPage = null;

        if (firstCatCd.equals("ALL") && secondCatCd.equals("ALL")) {
            productPage = productRepository.findAll(pageable);
        } else if (!firstCatCd.equals("ALL") && secondCatCd.equals("ALL")) {
            productPage = productRepository.findAllByLargeCat(firstCatCd, pageable);
        } else if (!firstCatCd.equals("ALL")) {
            productPage = productRepository.findByLargeCatAndSmallCatOrderByInsertTimeDesc(firstCatCd, secondCatCd, pageable);
        } else {
            throw new ProductListException("상품 리스트를 가져올 수 없습니다.");
        }

        if (productPage.getTotalElements() > 0) {
            return getAdminProductListMap(productPage, pageable);
        }

        return null;
    }

    // 상품 추가
    public String addProduct(ProductRequestDto productRequestDto) {

        productRepository.save(Product.builder()
                .productNm(productRequestDto.getProductNm())
                .price(productRequestDto.getPrice())
                .titleImg(productRequestDto.getTitleImg())
                .largeCat(productRequestDto.getLargeCtCode())
                .smallCat(productRequestDto.getSmallCtCode())
                .productStatus(ProductStatus.SALE)
                .cartList(new ArrayList<>())
                .productImgList(new ArrayList<>())
                .reviewList(new ArrayList<>())
                .purchaseCnt(0)
                .limitCnt(productRequestDto.getTotalCnt())
                .totalCnt(productRequestDto.getTotalCnt())
                .build());

        return "상품이 추가되었습니다.";
    }

    // 상품 상세조회
    public ProductResponseDto.AdminProductDetailResponseDto getAdminProductDetails(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (!productOpt.isPresent())
            throw new NotExistProductException("존재하지 않는 상품입니다.");

        Product product = productOpt.get();

        int disPrice = 0;
        List<LocalDateTime> disDateList = new ArrayList<>();

        LocalDateTime disStartDate = null;
        LocalDateTime disEndDate = null;
        String startMonthStr = "";
        String startDayStr = "";
        String endMonthStr = "";
        String endDayStr = "";

        if (disDateList.size() > 0) {
            disStartDate = disDateList.get(0);
            disEndDate = disDateList.get(1);

            startMonthStr = disStartDate.getMonthValue() < 10 ? "0" + disStartDate.getMonthValue() : "" + disStartDate.getMonthValue();
            startDayStr = disStartDate.getDayOfMonth() < 10 ? "0" + disStartDate.getDayOfMonth() : "" + disStartDate.getDayOfMonth();
            endMonthStr = disEndDate.getMonthValue() < 10 ? "0" + disEndDate.getMonthValue() : "" + disEndDate.getMonthValue();
            endDayStr = disEndDate.getDayOfMonth() < 10 ? "0" + disEndDate.getDayOfMonth() : "" + disEndDate.getDayOfMonth();
        }

        return ProductResponseDto.AdminProductDetailResponseDto.builder()
                .id(product.getId())
                .productNm(product.getProductNm())
                .price(product.getPrice())
                .disPrice(disPrice)
                .disStartDate(disStartDate == null ? "" : disStartDate.getYear() + "-" + startMonthStr + "-" + startDayStr)
                .disEndDate(disEndDate == null ? "" : disEndDate.getYear() + "-" + endMonthStr + "-" + endDayStr)
                .titleImg(product.getTitleImg())
                .largeCat(product.getLargeCat())
                .smallCat(product.getSmallCat())
                .totalCnt(product.getTotalCnt())
                .build();
    }

    // 상품 정보 수정
    public String updateProduct(Long id, ProductRequestDto.UpdateResponseDto updateRequestDto) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (!productOpt.isPresent())
            throw new NotExistProductException("존재하지 않는 상품입니다.");

        Product product = productOpt.get();

        product.setProductNm(updateRequestDto.getProductNm());
        product.setPrice(updateRequestDto.getPrice());
        product.setLargeCat(updateRequestDto.getLargeCtCode());
        product.setSmallCat(updateRequestDto.getSmallCtCode());
//        product.setTotal_cnt(updateRequestDto.getTotal_cnt());

        productRepository.save(product);

        return "상품 정보 수정이 완료되었습니다.";
    }

    private HashMap<String, Object> getResult(Page<Product> product, Pageable pageable) {
        List<ProductResponseDto> productResponseDtoList = getProductResponseDtoList(product);

        PageImpl<ProductResponseDto> productResponseDtoPage = new PageImpl<>(productResponseDtoList, pageable, product.getTotalElements());

        return getResultMap(productResponseDtoPage);
    }

    private List<ProductResponseDto> getProductResponseDtoList(Page<Product> products) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            int disPrice = 0;
            if (product.getProductDisPrcList().size() > 0) {
                disPrice = getDisPrice(product);
            }
            productResponseDtoList.add(product.toResponseDTO(disPrice));
        }

        return productResponseDtoList;
    }

    private Page<Product> getProductsByCategory(String catCd, Pageable pageable) {
        // 전체 상품 조회
        if(isNull(catCd) || catCd.equals("ALL")) {
            return productRepository.findAll(pageable);
        }
        // 카테고리로 상품 조회
        return productRepository.findBySmallCat(catCd, pageable);
    }

    private Page<Product> getProductsByCatCdAndSortCd(String catCd, String sortCd, int page) {
        Pageable pageable = getPageable(page, sortCd);

        // 정렬기준으로 전체상품 조회
        if(isNull(catCd) || catCd.equals("ALL")) {
            return productRepository.findAll(pageable);
        }
        // 카테고리, 정렬기준으로 상품 조회
        return productRepository.findAllByLargeCat(catCd, pageable);
    }

    private List<ProductResponseDto.MainProductResponseDto> getMainProductResponseDto(List<Product> products) {
        List<ProductResponseDto.MainProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            int disPrice = 0;
            if (product.getProductDisPrcList().size() > 0) {
                disPrice = getDisPrice(product);
            }
            productResponseDtoList.add(product.toMainProductResDTO(disPrice));
        }

        return productResponseDtoList;
    }

    // adminProductListDto 조회 공통
    private HashMap<String, Object> getAdminProductListMap(Page<Product> productPage, PageRequest pageable) {
        List<ProductResponseDto.AdminProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : productPage) {
            int disPrice = 0;
            productResponseDtoList.add(product.toAdminProductResDTO(disPrice));
        }

        PageImpl<ProductResponseDto.AdminProductResponseDto> adminProductResponseDtoPage
                = new PageImpl<>(productResponseDtoList, pageable, productPage.getTotalElements());

        PagingDto adminProductPagingDto = new PagingDto();
        adminProductPagingDto.setPagingInfo(adminProductResponseDtoPage);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("adminProductList", adminProductResponseDtoPage);
        resultMap.put("adminProductPagingDto", adminProductPagingDto);

        return resultMap;
    }

    private Pageable getPageable(int realPage, String sortCd) {
        Pageable pageable;

/*        switch (sortCd) {
            case "new":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.DESC, "insert_time"));
                break;
            case "past":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.ASC, "insert_time"));
                break;
            case "highPrice":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.DESC, "price", "insert_time"));
                break;
            case "lowPrice":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.ASC, "price", "insert_time"));
                break;
            case "highSell":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.DESC, "purchase_cnt", "insert_time"));
                break;
            case "lowSell":
                pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.ASC, "purchase_cnt", "insert_time"));
                break;
            default:
                throw new NoValidProductSortException("유효하지 않은 상품 정렬입니다.");
        }*/
        return null;
    }

    private int getDisPrice(Product product) {
        List<ProductDisPrice> disPriceList
                = product.getProductDisPrcList().stream()
                .filter(productDisPrice -> {
                    LocalDateTime now = LocalDateTime.now();

                    return now.isAfter(productDisPrice.getStartDate()) && now.isBefore(productDisPrice.getEndDate());
                }).sorted().limit(1).collect(Collectors.toList());

        if(disPriceList.size() > 0) {
            return disPriceList.get(0).getDisPrice();
        }

        return 0;
    }

    private HashMap<String, Object> getResultMap(PageImpl<ProductResponseDto> products) {

        PagingDto questionPagingDto = new PagingDto();
        questionPagingDto.setPagingInfo(products);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("productList", products);
        resultMap.put("productPagingDto", questionPagingDto);

        // PageImpl 객체를 반환
        return resultMap;
    }

    private ProductResponseDto.SaleProductResponseDto mapToDto(Map<String, Object> map) {

        Product saleProduct = (Product) map.get("product");
        Integer disPrice = (Integer) map.get("disPrc");

        return ProductResponseDto.SaleProductResponseDto.builder()
                .productId(saleProduct.getId())
                .productNm(saleProduct.getProductNm())
                .price(saleProduct.getPrice())
                .titleImg(saleProduct.getTitleImg())
                .rateAvg(saleProduct.getRateAvg())
                .disPrice(disPrice)
                .salePrice((int)((((float) 100 - (float) disPrice) / (float)100) * saleProduct.getPrice()))
                .build();
    }

    public String uploadProductImage(MultipartFile file, String dirName) throws IOException {
        // S3와 연결할 client 얻기
        AmazonS3 s3Client = awss3Utils.getS3Client();

        // S3에 저장할 파일 경로 얻기
        String saveFilePath = UploadFileUtils.getSaveFilePath(file, dirName);

        // S3에 파일 저장 후 url 반환
        return awss3Utils.putObjectToS3AndGetUrl(s3Client, saveFilePath, file);
    }
}
