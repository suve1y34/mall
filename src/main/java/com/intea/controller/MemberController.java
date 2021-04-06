package com.intea.controller;

import com.intea.constant.Const;
import com.intea.constant.Method;
import com.intea.domain.MemberEntity;
import com.intea.service.MemberService;
import com.intea.util.SecurityUtils;
import com.intea.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController extends UiUtils {

    private final MemberService mService;
    private final SecurityUtils sUtils;

    //회원가입 get
    @GetMapping("signup")
    public String getSignup() {
        return "user/member/join";
    }

    //로그인 get
    @GetMapping("signin")
    public String getLogin() {
        return "user/member/login";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/signout")
    public void logout() {
    }

/*    //회원가입 post
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/join", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Object> postSignup(@RequestBody(required = false) MemberEntity param) {
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("result", mService.insMember(param));

        return returnValue;
    }

    //아이디 중복체크
    @GetMapping(value = "/chkId/{userId}")
    @ResponseBody
    public Map<String, Object> ajaxChkId(MemberEntity param) {
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("result", mService.chkId(param));
        return returnValue;
    }
    


    //로그인 post
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Object> postLogin(@RequestBody(required = false) MemberEntity param, HttpServletRequest req) {

        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("result", mService.selMember(param, req));

        return returnValue;
    }
    

    
    // 내 정보 페이지
    @GetMapping("/user/modify")
    public String dispMyInfo(MemberEntity param, Model model, HttpServletRequest req) {
        MemberEntity loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();
        param.setI_mem(i_mem);

        int result = mService.getMyInfo(param);

        if(result == Const.FAIL) {
            return showMessageWithRedirect("회원 정보 불러오기에 실패하였습니다.", "/user/signin", Method.GET, null, model);
        }

        model.addAttribute("member", loginMem);
        return "user/member/myinfo";
    }

    //정보 수정
    @PostMapping("/user/upd")
    public String updMyInfo(MemberEntity param, HttpServletRequest req, Model model) {
        MemberEntity loginMem = sUtils.getLoginMem(req);
        Long i_mem = loginMem.getI_mem();

        param.setI_mem(i_mem);

        int result = mService.updMyInfo(param);

        if(result == Const.FAIL) {
            return showMessageWithRedirect("회원정보 수정에 실패하였습니다.", "/user/modify", Method.GET, null, model);
        }

        model.addAttribute("member", loginMem);

        return showMessageWithRedirect("회원정보 수정 완료", "/", Method.GET, null, model);
    }

    //아이디 찾기 get
    @RequestMapping(value = "user/find_id", method = RequestMethod.GET)
    public String findId() {
        return "user/member/find_id";
    }

    //아이디 찾기 ajax
    @RequestMapping(value = "/ajaxFindId", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxFindId(@RequestBody MemberEntity param) {
        String result = mService.findId(param);
        return result;
    }

    //비밀번호 찾기 get
    @RequestMapping(value = "user/find_pw", method = RequestMethod.GET)
    public String findPw() {
        return "user/member/find_pw";
    }

    //정보확인
    @RequestMapping(value = "/findPw", method = RequestMethod.POST)
    public String findPw(MemberEntity param, RedirectAttributes ra, Model model) {
        int result = mService.chkPw(param);
        if(result == Const.SUCCESS) {//정보가 맞는 경우
            ra.addFlashAttribute("data", param);
            return "redirect:/user/change_pw";
        } else if(result == Const.NO_ID) { //정보 없음
            ra.addFlashAttribute("data", param);
            return showMessageWithRedirect("정보를 찾을 수 없습니다. 다시 확인해 주세요.", "/user/find_pw", Method.GET, null, model);
        }
        return "redirect:/user/find_pw";
    }

    //비밀번호 업뎃 get
    @RequestMapping(value = "user/change_pw", method = RequestMethod.GET)
    public String changePw() {
        return "member/change_pw";
    }

    //비밀번호 업뎃 post
    @RequestMapping(value = "/changePw", method = RequestMethod.POST)
    public String changePw(MemberEntity param, Model model) {
        int result = mService.changePw(param);

        if(result == Const.FAIL) {
            return "redirect:/user/change_pw";
        }
        return showMessageWithRedirect("비밀번호 변경이 완료되었습니다.", "/user/signin", Method.GET, null, model);
    }

    //회원탈퇴 post
    @DeleteMapping("/user/{id}")
    public String postQuitMember(MemberEntity param, HttpSession hs, Model model) {
        mService.delMember(param);
        hs.invalidate();

        return showMessageWithRedirect("탈퇴가 완료되었습니다.", "/", Method.GET, null, model);
    }*/
}