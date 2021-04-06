package com.intea.controller;

import com.intea.constant.Method;
import com.intea.domain.BoardDTO;
import com.intea.service.QnaService;
import com.intea.util.SecurityUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class QnaController extends UiUtils {
    private final SecurityUtils sUtils;
    private final QnaService qService;

    @GetMapping(value = "/board/qna/reg")
    public String openQnaWrite(@ModelAttribute("param") BoardDTO param, @RequestParam(value = "idx", required = false) Long i_board, Model model, HttpSession hs) {
        if (i_board == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = qService.getQnaDetail(i_board);
            if (board == null) {
                return "redirect:/board/qna/list";
            }
            model.addAttribute("board", board);
        }

        return "board/qna/qna-regmod";
    }

    @PostMapping(value = "/board/qna/reg.do")
    public String registerQna(final BoardDTO param, Model model, HttpSession hs) {
        Map<String, Object> pagingParams = getPagingParams(param);

        param.setI_mem(sUtils.getLoginMemPk(hs));
        try {
            boolean isRegistered = qService.registerQna(param);
            if (isRegistered == false) {
                return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/qna/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/qna/list", Method.GET, null, model);

        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/qna/list", Method.GET, null, model);
        }

        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "board/qna/list", Method.GET, pagingParams, model);
    }

    @GetMapping(value = "/board/qna/list")
    public String openQnaList(Model model, BoardDTO param) {
        List<BoardDTO> boardList = qService.getQnaList(param);
        model.addAttribute("boardList", boardList);

        return "board/qna/qna-list";
    }

    @GetMapping(value = "/board/qna/view")
    public String openQnsDetail(@RequestParam(value = "idx", required = false) Long i_board, Model model) {
        if(i_board == null) {
            return "redirect:/board/qna/list";
        }
        BoardDTO board = qService.getQnaDetail(i_board);
        if(board == null || "Y".equals(board.getDelete_yn())) {
            return "redirect:/board/qna/list";
        }
        model.addAttribute("board", board);

        return "board/qna/qna-detail";
    }

    @PostMapping(value = "/board/qna/del")
    public String delQna(@RequestParam(value = "idx", required = false) Long i_board, Model model) {
        if(i_board == null) {
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/qna/list", Method.GET, null, model);

        }

        try {
            boolean isDel = qService.delQna(i_board);
            if(isDel == false) {
                return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/qna/list", Method.GET, null, model);
            }
        } catch(DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/qna/list", Method.GET, null, model);
        } catch(Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/qna/list", Method.GET, null, model);
        }
        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "board/qna/list", Method.GET, null, model);
    }
}
