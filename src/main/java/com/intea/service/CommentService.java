package com.intea.service;

import com.intea.constant.Const;
import com.intea.domain.CommentEntity;
import com.intea.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentMapper cMapper;


    public boolean registerComment(CommentEntity param) {
        int queryResult = Const.ZERO;

        if(param.getI_cmt() == null) {
            queryResult = cMapper.insComment(param);
        } else {
            queryResult = cMapper.updComment(param);
        }
        return (queryResult == Const.MINIMUM) ? true : false;
    }

    public boolean delComment(Long i_cmt) {
        int queryResult = Const.ZERO;

        CommentEntity comment = cMapper.selCommentDetail(i_cmt);

        if(comment != null && "N".equals(comment.getDelete_yn())) {
            queryResult = cMapper.delComment(i_cmt);
        }
        return (queryResult == Const.MINIMUM) ? true : false;
    }

    public List<CommentEntity> getCommentList(CommentEntity param) {
        List<CommentEntity> commentList = Collections.emptyList();

        int commentTotalCnt = cMapper.selCommentTotalCnt(param);
        if(commentTotalCnt > Const.ZERO) {
            commentList = cMapper.selCommentList(param);
        }

        return commentList;
    }
}
