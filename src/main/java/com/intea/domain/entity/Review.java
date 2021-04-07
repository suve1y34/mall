package com.intea.domain.entity;

import com.intea.constant.ReviewStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class Review extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_board;

    @ManyToOne
    @JoinColumn(name = "i_mem", referencedColumnName = "i_mem")
    private User user;

    @ManyToOne
    @JoinColumn(name = "i_product", referencedColumnName = "i_product")
    private Product product;

    private String title;
    private String content;
    private ReviewStatus grade;
}
