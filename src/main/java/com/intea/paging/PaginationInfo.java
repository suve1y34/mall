package com.intea.paging;

import com.intea.constant.Const;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {
    //페이징 계산에 필요한 파라미터들이 담긴 클래스
    private Criteria cri;
    //전체 데이터 갯수
    private int totalRecordCnt;
    //전체 페이지 갯수
    private int totalPageCnt;
    //페이지 리스트의 첫 페이지 번호
    private int firstPage;
    //페이지 리스트의 마지막 페이지 번호
    private int lastPage;
    //sql의 조건절에 사용되는 첫 rnum
    private int firstRecordIdx;
    //sql 조건절에 사용되는 마지막 rnum
    private int lastRecordIdx;
    //이전 페이지 존재 여부
    private boolean hasPrePage;
    //다음 페이지 존재 여부
    private boolean hasNextPage;

    public PaginationInfo(Criteria cri) {
        if(cri.getCurrentPageNo() < Const.MINIMUM) {
            cri.setCurrentPageNo(Const.MINIMUM);
        }
        if(cri.getRecordsPerPage() < Const.MINIMUM || cri.getRecordsPerPage() > Const.HUNDRED) {
            cri.setRecordsPerPage(Const.RECORDS);
        }
        if(cri.getPageSize() < Const.FIVE || cri.getPageSize() > Const.TWENTY) {
            cri.setPageSize(Const.RECORDS);
        }

        this.cri = cri;
    }

    public void setTotalRecordCnt(int totalRecordCnt) {
        this.totalRecordCnt = totalRecordCnt;

        if(totalRecordCnt > Const.ZERO) {
            calculation();
        }
    }

    private void calculation() {
        //전체 페이지 수(현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장
        totalPageCnt = ((totalRecordCnt - Const.MINIMUM) / cri.getRecordsPerPage()) + Const.MINIMUM;
        if(cri.getCurrentPageNo() > totalPageCnt) {
            cri.setCurrentPageNo(totalPageCnt);
        }

        //페이지 리스트의 첫 페이지 번호
        firstPage = ((cri.getCurrentPageNo() - Const.MINIMUM) / cri.getPageSize()) * cri.getPageSize() + Const.MINIMUM;

        //페이지 리스트의 마지막 페이지 번호(마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장
        lastPage = firstPage + cri.getPageSize() - Const.MINIMUM;
        if(lastPage > totalPageCnt) {
            lastPage = totalPageCnt;
        }

        //sql의 조건절에 사용되는 첫 rnum
        firstRecordIdx = (cri.getCurrentPageNo() - Const.MINIMUM) * cri.getRecordsPerPage();
        //sql의 조건절에 사용되는 마지막 rnum
        lastRecordIdx = cri.getCurrentPageNo() * cri.getRecordsPerPage();

        //이전 페이지 존재 여부
        hasPrePage = firstPage != Const.MINIMUM;
        //다음 페이지 존재 여부
        hasNextPage = (lastPage * cri.getRecordsPerPage()) < totalRecordCnt;
    }
}
