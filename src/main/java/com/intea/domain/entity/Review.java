package com.intea.domain.entity;

import com.intea.domain.dto.ReviewResDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Review extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private String title;
    private String content;
    private int rate;

    public ReviewResDTO toResDTO() {
        LocalDateTime insert_time = this.getInsert_time();

        return ReviewResDTO.builder()
                .id(id)
                .user_id(user.toReviewResDTO())
                .title(title)
                .rate(rate)
                .insert_time(insert_time.getYear() + "-" + insert_time.getMonthValue() + "-" +
                        insert_time.getDayOfMonth() + " " + insert_time.getHour() + ":" + insert_time.getMinute() + ":" + insert_time.getSecond())
                .build();
    }
}
