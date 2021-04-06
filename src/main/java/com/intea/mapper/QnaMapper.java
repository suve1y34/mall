package com.intea.mapper;

import com.intea.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {
    public int insQna(BoardDTO param);
    public BoardDTO selQnaDetail(Long i_board);
    public int updQna(BoardDTO param);
    public int delQna(Long i_board);
    public List<BoardDTO> selQnaList(BoardDTO param);
    public int selQnaTotalCnt(BoardDTO param);
}
