package com.intea.service;

import com.intea.constant.Const;
import com.intea.domain.BoardDTO;
import com.intea.mapper.QnaMapper;
import com.intea.paging.PaginationInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class QnaService {

    private QnaMapper qMapper;

    public boolean registerQna(BoardDTO param) {
        int queryResult = 0;
        if(param.getI_board() == null) {
            queryResult = qMapper.insQna(param);
        } else {
            queryResult = qMapper.updQna(param);
        }
        return (queryResult == 1) ? true : false;
    }

    public BoardDTO getQnaDetail(Long i_board) {
        return qMapper.selQnaDetail(i_board);
    }

    public boolean delQna(Long i_board) {
        int queryResult = 0;
        BoardDTO board = qMapper.selQnaDetail(i_board);

        if(board != null && "N".equals(board.getDelete_yn())) {
            queryResult = qMapper.delQna(i_board);
        }
        return (queryResult == 1) ? true : false;
    }

    public List<BoardDTO> getQnaList(BoardDTO param) {
        List<BoardDTO> qnaList = Collections.emptyList();
        int qnaTotal = qMapper.selQnaTotalCnt(param);

        PaginationInfo paginationInfo = new PaginationInfo(param);
        paginationInfo.setTotalRecordCnt(qnaTotal);

        if(qnaTotal > Const.ZERO) {
            qnaList = qMapper.selQnaList(param);
        }
        return qnaList;
    }
}
