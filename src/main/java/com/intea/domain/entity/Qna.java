package com.intea.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class Qna extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_board;

    @ManyToOne
    @JoinColumn(name = "i_mem", referencedColumnName = "i_mem")
    private User user;

    private String title;
    private String content;
}
