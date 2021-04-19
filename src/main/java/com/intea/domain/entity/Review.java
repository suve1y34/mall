package com.intea.domain.entity;

import com.intea.domain.dto.ReviewResponseDto;
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

    public ReviewResponseDto toResponseDTO() {
        LocalDateTime insertTime = this.getInsertTime();

        return ReviewResponseDto.builder()
                .id(id)
                .userId(user.toReviewResDTO())
                .title(title)
                .rate(rate)
                .insertTime(insertTime.getYear() + "-" + insertTime.getMonthValue() + "-" +
                        insertTime.getDayOfMonth() + " " + insertTime.getHour() + ":" + insertTime.getMinute() + ":" + insertTime.getSecond())
                .build();
    }
}
