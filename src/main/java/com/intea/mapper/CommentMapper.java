package com.intea.mapper;

import com.intea.domain.CommentEntity;
import com.intea.domain.CommonEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public int insComment(CommentEntity param);
    public CommentEntity selCommentDetail(Long i_cmt);
    public int updComment(CommentEntity param);
    public int delComment(Long i_cmt);
    public List<CommentEntity> selCommentList(CommonEntity param);
    public int selCommentTotalCnt(CommentEntity param);
}
