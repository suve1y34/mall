package com.intea.service;

import com.intea.constant.Const;
import com.intea.constant.Role;
import com.intea.domain.dto.CategoryRequestDto;
import com.intea.domain.dto.CategoryResponseDto;
import com.intea.domain.entity.Category;
import com.intea.domain.repository.CategoryRepository;
import com.intea.exception.CategoryCodeException;
import com.intea.exception.NotExistCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
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
//    private final ValueOperations valueOperations;

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getCategoryList() {
//        return (HashMap<String, Object>) valueOperations.get(Const.CATEGORY_LIST_KEY);
        return null;
    }

    /**
     * 상위 카테고리 추가, 추가 후 캐시 업데이트
     */
    public String addFirstCategory(CategoryRequestDto.BigCategory bigCategory) {
        // Mysql에 추가 (File i/o)
        saveFirstCategory(bigCategory);

        return "1차 카테고리가 등록 되었습니다.";
    }

    /**
     * 하위 카테고리 추가, 추가 후 캐시 업데이트
     */
    public String addSecondCategory(CategoryRequestDto.SmallCategory smallCategory) {
        // Mysql에 추가 (File i/o)
        saveSecondCategory(smallCategory);

        return "2차 카테고리가 등록 되었습니다.";
    }

    // 카테고리 상세
    @Transactional
    public ResponseEntity<?> getDetailOfCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (!categoryOpt.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리 입니다.");

        Category category = categoryOpt.get();

        if (category.getCtLevel() == 1) {
            return ResponseEntity.ok().body(CategoryResponseDto.BigCategory.builder()
                    .id(category.getId())
                    .catNm(category.getCatNm())
                    .useYn(category.getUseYn())
                    .build());
        } else {
            return ResponseEntity.ok().body(CategoryResponseDto.SmallCategory.builder()
                    .id(category.getId())
                    .catNm(category.getCatNm())
                    .upperCatCode(categoryRepository.findByCatCode(category.getUpperCatCode()).getCatNm())
                    .useYn(category.getUseYn())
                    .build());
        }
    }

    /**
     * 카테고리 수정 시, 캐시 업데이트
     */
    public String updateCategory(Long id, CategoryRequestDto categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new NotExistCategoryException("존재하지 않는 카테고리 입니다."));

        category.setCatNm(categoryDTO.getC_nm());
        category.setUseYn(categoryDTO.getUseYn());

        categoryRepository.save(category);

        return "카테고리가 수정되었습니다.";
    }

    // 2차 카테고리 리스트 조회
    public List<CategoryResponseDto.SmallCategory> getSecondCategoryList(String firstCatCd) {

        List<Category> secondCategoryList = categoryRepository.findAllByUpperCatCode(firstCatCd);

        List<CategoryResponseDto.SmallCategory> secondCategoryDtoList = new ArrayList<>();

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
                    filter(productCat -> productCat.getUseYn() == 'Y').collect(Collectors.toList()));
        }

        return resultMap;
    }

    private void saveFirstCategory(CategoryRequestDto.BigCategory bigCategory) {
        List<Category> firstCategoryList = categoryRepository.findAllByCtLevelOrderByCatCodeDesc(1);

        String catCdOfFinalBigCategory;

        if(firstCategoryList.isEmpty()) {
            catCdOfFinalBigCategory = "C010000";
        } else {
            catCdOfFinalBigCategory = makeFirstCatCd(firstCategoryList.get(0).getCatCode());
        }

        categoryRepository.save(Category.builder()
                .catCode(catCdOfFinalBigCategory)
                .ctLevel(1)
                .catNm(bigCategory.getCatNm())
                .useYn(bigCategory.getUseYn())
                .build());
    }

    private void saveSecondCategory(CategoryRequestDto.SmallCategory smallCategory) {
        String upprCatCd = smallCategory.getUpperCatCode();

        List<Category> secondCategoryList = categoryRepository.findAllByUpperCatCodeOrderByCatCodeDesc(upprCatCd);

        String catCdOfNewSmallCategory;

        if (secondCategoryList.isEmpty()) {
            catCdOfNewSmallCategory = makeSecondCatCd(upprCatCd);
        } else {
            String catCdOfFinalSmallCategory = secondCategoryList.get(0).getCatCode();

            catCdOfNewSmallCategory = makeSecondCatCd(catCdOfFinalSmallCategory);
        }

        categoryRepository.save(Category.builder()
                .catCode(catCdOfNewSmallCategory)
                .ctLevel(2)
                .catNm(smallCategory.getCatNm())
                .upperCatCode(upprCatCd)
                .cnntUrl("/productList")
                .useYn(smallCategory.getUseYn())
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
}
