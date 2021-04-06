package com.intea.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@ToString
@Entity(name = "p_category")
public class Category {
    @Id
    private Long i_category;
    private Long c_level;
    private String c_code;
    private String c_nm;
}
