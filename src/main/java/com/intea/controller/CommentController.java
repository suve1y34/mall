package com.intea.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intea.adapter.GsonLocalDateTimeAdapter;
import com.intea.domain.CommentEntity;
import com.intea.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService cService;

    @RequestMapping(value = {"/comments", "/comments/{i_cmt}"}, method = {RequestMethod.POST, RequestMethod.PATCH})
    public JsonObject registerComment(@PathVariable(value = "i_cmt", required = false) Long i_cmt
                                    , @RequestBody final CommentEntity param) {
        JsonObject jsonObj = new JsonObject();

        try {
            if(i_cmt != null) {
                param.setI_cmt(i_cmt);

                boolean isRegistered = cService.registerComment(param);
                jsonObj.addProperty("result", isRegistered);
            }
        } catch(DataAccessException e) {
            jsonObj.addProperty("message", "DB 처리 과정에 문제가 발생하였습니다.");
        } catch(Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }
        return jsonObj;
    }

    @GetMapping(value = "/comments/{i_board}")
    public JsonObject getCommentList(@PathVariable("i_board") Long i_board
                            , @ModelAttribute("param") CommentEntity param) {
        JsonObject jsonObj = new JsonObject();

        List<CommentEntity> commentList = cService.getCommentList(param);
        if(CollectionUtils.isEmpty(commentList) == false) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
            JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
            jsonObj.add("commentList", jsonArr);
        }
        return jsonObj;
    }
    
    @DeleteMapping(value = "/comments/{i_cmt}")
    public JsonObject deleteComment(@PathVariable("i_cmt") final Long i_cmt) {
        JsonObject jsonObj = new JsonObject();
        
        try {
            boolean isDeleted = cService.delComment(i_cmt);
            jsonObj.addProperty("result", isDeleted);
        } catch(DataAccessException e) {
            jsonObj.addProperty("message", "DB처리 과정에 문제가 발생하였습니다.");
        } catch(Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }
        return jsonObj;
    }
}