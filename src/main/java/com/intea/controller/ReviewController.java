package com.intea.controller;

import com.intea.constant.Method;
import com.intea.domain.BoardDTO;
import com.intea.service.ReviewService;
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
public class ReviewController extends UiUtils {
    private final SecurityUtils sUtils;
    private final ReviewService rService;

    @GetMapping(value = "/board/review/reg")
    public String openReviewWrite(@ModelAttribute("param") BoardDTO param, @RequestParam(value = "idx", required = false) Long i_board, Model model, HttpSession hs) {
        if (i_board == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = rService.getReviewDetail(i_board);
            if (board == null) {
                return "redirect:/board/review/list";
            }
            model.addAttribute("board", board);
        }

        return "board/review/review-regmod";
    }

    @PostMapping(value = "/board/review/reg.do")
    public String registerReview(final BoardDTO param, Model model, HttpSession hs) {
        Map<String, Object> pagingParams = getPagingParams(param);

        param.setI_mem(sUtils.getLoginMemPk(hs));
        try {
            boolean isRegistered = rService.registerReview(param);
            if (isRegistered == false) {
                return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/review/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/review/list", Method.GET, null, model);

        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/review/list", Method.GET, null, model);
        }

        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "board/review/list", Method.GET, pagingParams, model);
    }

    @GetMapping(value = "/board/review/list")
    public String openReviewList(Model model, BoardDTO param) {
        List<BoardDTO> boardList = rService.getReviewList(param);
        model.addAttribute("boardList", boardList);

        return "board/review/review-list";
    }

    @PostMapping(value = "/review/list")
    public void openReviewListOfProduct(Model model, Long i_product) {
        List<BoardDTO> board = rService.getReviewListOfProduct(i_product);
        model.addAttribute("boardList", board);
    }

    @GetMapping(value = "/board/review/view")
    public String openReviewDetail(@RequestParam(value = "idx", required = false) Long i_board, Model model) {
        if(i_board == null) {
            return "redirect:/board/review/list";
        }
        BoardDTO board = rService.getReviewDetail(i_board);
        if(board == null || "Y".equals(board.getDelete_yn())) {
            return "redirect:/board/review/list";
        }
        model.addAttribute("board", board);

        return "board/review/review-detail";
    }

    @PostMapping(value = "/board/review/del")
    public String delReview(@RequestParam(value = "idx", required = false) Long i_board, Model model) {
        if(i_board == null) {
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/review/list", Method.GET, null, model);

        }

        try {
            boolean isDel = rService.delReview(i_board);
            if(isDel == false) {
                return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/review/list", Method.GET, null, model);
            }
        } catch(DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/review/list", Method.GET, null, model);
        } catch(Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/review/list", Method.GET, null, model);
        }
        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "board/review/list", Method.GET, null, model);
    }
}
