package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageImpl;

@Getter
@Setter
@ToString
public class PagingDTO {
    private int page = 1;               //현재
    private int scale = 3;
    private int scaleStartPage = 1;     //scale 시작
    private int scaleEndPage = 1;       //scale 끝
    private int totPage = 1;            //전체 페이지 수
    private int prePage = 0;            //이전 페이지
    private int nextPage = 0;           //다음 페이지

    public void setPagingInfo(PageImpl<?> list) {
        this.page = list.getNumber() + 1;
        int totPage = list.getTotalPages();

        int nowScale = (page - 1) / this.scale + 1;         //현재 화면 그룹

        int startPage = (nowScale - 1) * scale + 1;         //scale 시작
        int endPage = startPage + scale - 1;                //scale 끝

        endPage = (endPage > totPage) ? totPage : endPage;

        int prePage = startPage - 1;                        //이전
        int nextPage = endPage + 1;                         //다음

        this.scaleStartPage = startPage;
        this.scaleEndPage = endPage;
        this.prePage = prePage;
        this.nextPage = nextPage;
        this.totPage = totPage;
    }
}
