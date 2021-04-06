package com.intea.service;

import com.intea.constant.Const;
import com.intea.domain.BoardDTO;
import com.intea.mapper.ReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class ReviewService {
    private ReviewMapper rMapper;

    public boolean registerReview(BoardDTO param) {
        int queryResult = 0;
        if(param.getI_board() == null) {
            queryResult = rMapper.insReview(param);
        } else {
            queryResult = rMapper.updReview(param);
        }
        return (queryResult == 1) ? true : false;
    }

    public BoardDTO getReviewDetail(Long i_board) {
        return rMapper.selReviewDetail(i_board);
    }

    public boolean delReview(Long i_board) {
        int queryResult = 0;
        BoardDTO board = rMapper.selReviewDetail(i_board);

        if(board != null && "N".equals(board.getDelete_yn())) {
            queryResult = rMapper.delReview(i_board);
        }
        return (queryResult == 1) ? true : false;
    }

    public List<BoardDTO> getReviewList(BoardDTO param) {
        List<BoardDTO> review = Collections.emptyList();
        int reviewTotal = rMapper.selReviewTotalCnt(param);

        if(reviewTotal > 0) {
            review = rMapper.selReviewList(param);
        }
        return review;
    }

    public List<BoardDTO> getReviewListOfProduct(Long i_product) {
        List<BoardDTO> review = Collections.emptyList();
        int reviewTotal = rMapper.selReviewCnt(i_product);

        if(reviewTotal > Const.ZERO) {
            review = rMapper.selReviewListOfProduct(i_product);
        }
        return review;
    }
}
