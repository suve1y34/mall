package com.intea.domain.entity;

import com.intea.domain.dto.CategoryResDTO;
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
    private Long c_level;
    private String upper_c_code;
    private String c_code;
    private String c_nm;
    private Character use_yn;

    public CategoryResDTO.SmallCategory toSmallCategoryDTO() {
        return CategoryResDTO.SmallCategory.builder()
                .id(id)
                .c_code(c_code)
                .c_nm(c_nm)
                .upper_c_code(upper_c_code)
                .use_yn(use_yn)
                .build();
    }
}
