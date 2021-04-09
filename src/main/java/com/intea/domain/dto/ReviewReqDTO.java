package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ReviewReqDTO {

    private UUID user_id;
    private Long product_id;
    @NotBlank(message = "제목을 작성해 주세요.")
    private String title;

    @Min(value = 1, message = "평점은 1보다 작을 수 없습니다.")
    @Max(value = 5, message = "평점은 5보다 작을 수 없습니다.")
    private int rate;

    @NotBlank(message = "내용을 작성해 주세요.")
    private String content;
}
