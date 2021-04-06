package com.intea.mapper;

import com.intea.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    public int insReview(BoardDTO param);
    public BoardDTO selReviewDetail(Long i_board);
    public int updReview(BoardDTO param);
    public int delReview(Long i_board);
    public List<BoardDTO> selReviewList(BoardDTO param);
    public List<BoardDTO> selReviewListOfProduct(Long i_product);
    public int selReviewTotalCnt(BoardDTO param);
    public int selReviewCnt(Long i_product);
}
