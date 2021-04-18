package com.intea.service;

import com.intea.constant.Role;
import com.intea.domain.dto.CategoryReqDTO;
import com.intea.domain.dto.CategoryResDTO;
import com.intea.domain.entity.Category;
import com.intea.domain.repository.CategoryRepository;
import com.intea.exception.CategoryCodeException;
import com.intea.exception.NotExistCategoryException;
import com.nimbusds.openid.connect.sdk.federation.policy.operations.ValueOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.DynamicAny.DynValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j @RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
//    private final ValueOperations valueOperation;

/*    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getCategoryList() {
        return (HashMap<String, Object>) valueOperation.get(CATEGORY_LIST_KEY);
    }*/

    /**
     * 상위 카테고리 추가, 추가 후 캐시 업데이트
     */
    public String addFirstCategory(CategoryReqDTO.BigCategory bigCategory) {
        // Mysql에 추가 (File i/o)
        saveFirstCategory(bigCategory);

        // 상품 카테고리 캐싱 (Memory i/o)
        setCategoryCaching();

        return "1차 카테고리가 등록 되었습니다.";
    }

    /**
     * 하위 카테고리 추가, 추가 후 캐시 업데이트
     */
    public String addSecondCategory(CategoryReqDTO.SmallCategory smallCategory) {
        // Mysql에 추가 (File i/o)
        saveSecondCategory(smallCategory);

        // 상품 카테고리 캐싱 (Memory i/o)
        setCategoryCaching();

        return "2차 카테고리가 등록 되었습니다.";
    }

    // 카테고리 상세
    @Transactional
    public ResponseEntity<?> getDetailOfCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (!categoryOpt.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리 입니다.");

        Category category = categoryOpt.get();

        if (category.getC_level() == 1) {
            return ResponseEntity.ok().body(CategoryResDTO.BigCategory.builder()
                    .id(category.getId())
                    .c_nm(category.getC_nm())
                    .use_yn(category.getUse_yn())
                    .build());
        } else {
            return ResponseEntity.ok().body(CategoryResDTO.SmallCategory.builder()
                    .id(category.getId())
                    .c_nm(category.getC_nm())
                    .upper_c_code(categoryRepository.findByC_code(category.getUpper_c_code()).getC_nm())
                    .use_yn(category.getUse_yn())
                    .build());
        }
    }

    /**
     * 카테고리 수정 시, 캐시 업데이트
     */
    public String updateCategory(Long id, CategoryReqDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new NotExistCategoryException("존재하지 않는 카테고리 입니다."));

        category.setC_nm(categoryDTO.getC_nm());
        category.setUse_yn(categoryDTO.getUse_yn());

        categoryRepository.save(category);

        // 캐시 업데이트
        setCategoryCaching();

        return "카테고리가 수정되었습니다.";
    }

    // 2차 카테고리 리스트 조회
    public List<CategoryResDTO.SmallCategory> getSecondCategoryList(String firstCatCd) {

        List<Category> secondCategoryList = categoryRepository.findAllByUpper_c_code(firstCatCd);

        List<CategoryResDTO.SmallCategory> secondCategoryDtoList = new ArrayList<>();

        for (Category productCat : secondCategoryList) {
            secondCategoryDtoList.add(productCat.toSmallCategoryDTO());
        }

        return secondCategoryDtoList;
    }

    public HashMap<String, Object> getAdminCategoryList() {
        HashMap<String, Object> resultMap = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(String.valueOf(authentication.getAuthorities()).contains(Role.ADMIN.getKey())) {
            List<Category> allCategoryList = categoryRepository.findAll();

            resultMap.put("adminCatList", allCategoryList);
            resultMap.put("mainCatList", allCategoryList.stream().
                    filter(productCat -> productCat.getUse_yn() == 'Y').collect(Collectors.toList()));
        }

        return resultMap;
    }

    private void saveFirstCategory(CategoryReqDTO.BigCategory bigCategory) {
        List<Category> firstCategoryList = categoryRepository.findAllByC_levelOrderByC_codeDesc(1);

        String catCdOfFinalBigCategory;

        if(firstCategoryList.isEmpty()) {
            catCdOfFinalBigCategory = "C010000";
        } else {
            catCdOfFinalBigCategory = makeFirstCatCd(firstCategoryList.get(0).getC_code());
        }

        categoryRepository.save(Category.builder()
                .c_code(catCdOfFinalBigCategory)
                .c_level(1)
                .catNm(bigCategory.getC_nm())
                .useYn(bigCategory.getUse_yn())
                .build());
    }

    private void saveSecondCategory(CategoryReqDTO.SmallCategory smallCategory) {
        String upprCatCd = smallCategory.getUpper_c_code();

        List<Category> secondCategoryList = categoryRepository.findAllByUpper_c_codeOrderByC_codeDesc(upprCatCd);

        String catCdOfNewSmallCategory;

        if (secondCategoryList.isEmpty()) {
            catCdOfNewSmallCategory = makeSecondCatCd(upprCatCd);
        } else {
            String catCdOfFinalSmallCategory = secondCategoryList.get(0).getC_code();

            catCdOfNewSmallCategory = makeSecondCatCd(catCdOfFinalSmallCategory);
        }

        categoryRepository.save(Category.builder()
                .c_code(catCdOfNewSmallCategory)
                .c_level(2)
                .catNm(smallCategory.getC_nm())
                .upprCatCd(upprCatCd)
                .cnntUrl("/productList")
                .useYn(smallCategory.getUse_yn())
                .build());
    }

    private String makeFirstCatCd(String catCdOfFinalBigCategory) {
        String catCdStr = catCdOfFinalBigCategory.split("C")[1].split("000")[0];

        int catCdInt = Integer.parseInt(catCdStr);

        int newCatCdInt = catCdInt + 1;

        if (newCatCdInt >= 1000) {
            throw new CategoryCodeException("더 이상 상위 카테고리를 추가할 수 없습니다.");
        }

        StringBuilder newCatCdSb = new StringBuilder();
        newCatCdSb.append(newCatCdInt);

        while(newCatCdSb.toString().length() < 3) {
            newCatCdSb.append("0");
        }

        newCatCdSb.append("C").reverse().append("000");

        return newCatCdSb.toString();
    }

    private String makeSecondCatCd(String catCd) {

        int newCatCdInt = Integer.parseInt(catCd.split("C")[1].substring(3)) + 1;

        if (newCatCdInt >= 1000) {
            throw new CategoryCodeException("더 이상 하위 카테고리를 추가할 수 없습니다.");
        }

        catCd = catCd.substring(0, 3) + (Integer.parseInt(catCd.split("C")[1]) + 1);

        return catCd;
    }

    /**
     * 카테고리 추가 시 캐시 업데이트
     */
    private void setCategoryCaching() {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("mainCatList", categoryRepository.findAllByUse_yn('Y'));

//        valueOperations.set(CATEGORY_LIST_KEY, resultMap);
    }

}
