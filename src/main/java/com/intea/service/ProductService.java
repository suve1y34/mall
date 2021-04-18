package com.intea.service;

import com.intea.constant.ProductStatus;
import com.intea.domain.dto.PagingDTO;
import com.intea.domain.dto.ProductReqDTO;
import com.intea.domain.dto.ProductResDTO;
import com.intea.domain.entity.Product;
import com.intea.domain.repository.ProductRepository;
import com.intea.exception.NoValidProductSortException;
import com.intea.exception.NotExistProductException;
import com.intea.exception.ProductListException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
//    private final ZSetOperations<String, Object> zSetOperations;

    // 전체 상품 혹은 카테고리로 상품 조회
    public HashMap<String, Object> getProductList(String catCd, String sortCd, String saleCd, int page) throws Exception {
        int realPage = (page == 0) ? 0 : page - 1;
        Pageable pageable = PageRequest.of(realPage, 9, new Sort(Sort.Direction.DESC, "createdDate"));

        // 카테고리로 조회
        if(isNull(sortCd) && isNull(saleCd)) {
            return getResult(getProductsByCategory(catCd, pageable), pageable);
        }

        // 정렬 기준으로 조회
        if(nonNull(sortCd) && nonNull(catCd) && isNull(saleCd)) {
            return getResult(getProductsByCatCdAndSortCd(catCd, sortCd, realPage), pageable);
        }

        throw new NoValidProductSortException("유효하지 않은 상품 조회 요청 파라미터 입니다.");
    }

    // 상품 상세
    public ProductResDTO getProductDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NotExistProductException("존재하지 않는 상품입니다."));

/*        int disPrice = 0;
        if (product.getProductList().size() > 0) {
            disPrice = getDisPrice(product);
        }*/

        return product.toResponseDTO();
    }

    /**
     * 인기 상위 10개의 상품 조회
     */
    public List<ProductResDTO.MainProductResDTO> getBestProductList() {
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
    public List<ProductResDTO.MainProductResDTO> getNewProductList() {
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
        PageRequest pageable = PageRequest.of(realPage, 10, new Sort(Sort.Direction.DESC, "createdDate"));

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
        PageRequest pageable = PageRequest.of(realPage, 10, new Sort(Sort.Direction.DESC, "createdDate"));

        Page<Product> productPage = null;

        if (firstCatCd.equals("ALL") && secondCatCd.equals("ALL")) {
            productPage = productRepository.findAll(pageable);
        } else if (!firstCatCd.equals("ALL") && secondCatCd.equals("ALL")) {
            productPage = productRepository.findAllByLarge_ct(firstCatCd, pageable);
        } else if (!firstCatCd.equals("ALL")) {
            productPage = productRepository.findByLarge_ctAndSmall_ctOrderByInsert_timeDesc(firstCatCd, secondCatCd, pageable);
        } else {
            throw new ProductListException("상품 리스트를 가져올 수 없습니다.");
        }

        if (productPage.getTotalElements() > 0) {
            return getAdminProductListMap(productPage, pageable);
        }

        return null;
    }

    // 상품 추가
    public String addProduct(ProductReqDTO productRequestDto) {

        productRepository.save(Product.builder()
                .p_nm(productRequestDto.getP_nm())
                .price(productRequestDto.getPrice())
                .title_img(productRequestDto.getTitle_img())
                .large_ct(productRequestDto.getLarge_ct())
                .small_ct(productRequestDto.getSmall_ct())
                .product_status(ProductStatus.SALE)
                .cartList(new ArrayList<>())
                .productImgList(new ArrayList<>())
                .reviewList(new ArrayList<>())
                .purchase_cnt(0)
                .limit_cnt(productRequestDto.getTotal_cnt())
                .total_cnt(productRequestDto.getTotal_cnt())
                .build());

        return "상품이 추가되었습니다.";
    }

    // 상품 상세조회
    public ProductResDTO.AdminProductDetailResDTO getAdminProductDetails(Long id) {
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

        return ProductResDTO.AdminProductDetailResDTO.builder()
                .id(product.getId())
                .p_nm(product.getP_nm())
                .price(product.getPrice())
                .title_img(product.getTitle_img())
                .large_ct(product.getLarge_ct())
                .small_ct(product.getSmall_ct())
                .total_cnt(product.getTotal_cnt())
                .build();
    }

    // 상품 정보 수정
    public String updateProduct(Long id, ProductReqDTO.UpdateResDTO updateRequestDto) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (!productOpt.isPresent())
            throw new NotExistProductException("존재하지 않는 상품입니다.");

        Product product = productOpt.get();

        product.setP_nm(updateRequestDto.getP_nm());
        product.setPrice(updateRequestDto.getPrice());
        product.setLarge_ct(updateRequestDto.getLarge_ct());
        product.setSmall_ct(updateRequestDto.getSmall_ct());
//        product.setTotal_cnt(updateRequestDto.getTotal_cnt());

        productRepository.save(product);

        return "상품 정보 수정이 완료되었습니다.";
    }

    private HashMap<String, Object> getResult(Page<Product> product, Pageable pageable) {
        List<ProductResDTO> productResponseDtoList = getProductResponseDtoList(product);

        PageImpl<ProductResDTO> productResponseDtoPage = new PageImpl<>(productResponseDtoList, pageable, product.getTotalElements());

        return getResultMap(productResponseDtoPage);
    }

    private List<ProductResDTO> getProductResponseDtoList(Page<Product> products) {
        List<ProductResDTO> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            int disPrice = 0;
/*            if (product.getProductList().size() > 0) {
                disPrice = getDisPrice(product);
            }*/
            productResponseDtoList.add(product.toResponseDTO());
        }

        return productResponseDtoList;
    }

    private Page<Product> getProductsByCategory(String catCd, Pageable pageable) {
        // 전체 상품 조회
        if(isNull(catCd) || catCd.equals("ALL")) {
            return productRepository.findAll(pageable);
        }
        // 카테고리로 상품 조회
        return productRepository.findBySmall_ct(catCd, pageable);
    }

    private Page<Product> getProductsByCatCdAndSortCd(String catCd, String sortCd, int page) {
        Pageable pageable = getPageable(page, sortCd);

        // 정렬기준으로 전체상품 조회
        if(isNull(catCd) || catCd.equals("ALL")) {
            return productRepository.findAll(pageable);
        }
        // 카테고리, 정렬기준으로 상품 조회
        return productRepository.findAllByLarge_ct(catCd, pageable);
    }

    private List<ProductResDTO.MainProductResDTO> getMainProductResponseDto(List<Product> products) {
        List<ProductResDTO.MainProductResDTO> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            int disPrice = 0;
            productResponseDtoList.add(product.toMainProductResDTO());
        }

        return productResponseDtoList;
    }

    // adminProductListDto 조회 공통
    private HashMap<String, Object> getAdminProductListMap(Page<Product> productPage, PageRequest pageable) {
        List<ProductResDTO.AdminProductResDTO> productResponseDtoList = new ArrayList<>();

        for (Product product : productPage) {
            int disPrice = 0;
            productResponseDtoList.add(product.toAdminProductResDTO(disPrice));
        }

        PageImpl<ProductResDTO.AdminProductResDTO> adminProductResponseDtoPage
                = new PageImpl<>(productResponseDtoList, pageable, productPage.getTotalElements());

        PagingDTO adminProductPagingDto = new PagingDTO();
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
        return pageable;
    }

    private HashMap<String, Object> getResultMap(PageImpl<ProductResDTO> products) {

        PagingDTO questionPagingDto = new PagingDTO();
        questionPagingDto.setPagingInfo(products);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("productList", products);
        resultMap.put("productPagingDto", questionPagingDto);

        // PageImpl 객체를 반환
        return resultMap;
    }

/*    private ProductResDTO.SaleProductResponseDto mapToDto(Map<String, Object> map) {

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
    }*/

/*    public String uploadProductImage(MultipartFile file, String dirName) throws IOException {
        // S3와 연결할 client 얻기
        AmazonS3 s3Client = awss3Utils.getS3Client();

        // S3에 저장할 파일 경로 얻기
        String saveFilePath = UploadFileUtils.getSaveFilePath(file, dirName);

        // S3에 파일 저장 후 url 반환
        return awss3Utils.putObjectToS3AndGetUrl(s3Client, saveFilePath, file);
    }*/
}
