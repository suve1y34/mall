package com.intea.domain.entity;

import com.intea.domain.dto.CategoryResponseDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "p_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ctLevel;
    private String upprCatCode;
    private String catCode;
    private String catNm;
    private Character useYn;
    private String cnntUrl;

    public CategoryResponseDto.SmallCategory toSmallCategoryDTO() {
        return CategoryResponseDto.SmallCategory.builder()
                .id(id)
                .catCode(catCode)
                .catNm(catNm)
                .upprCatCode(upprCatCode)
                .useYn(useYn)
                .build();
    }
}
