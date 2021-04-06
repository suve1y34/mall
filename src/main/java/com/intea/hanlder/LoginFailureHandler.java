package com.intea.hanlder;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
                                        AuthenticationException exception) throws IOException, ServletException {

        req.setAttribute("mem_id", req.getParameter("mem_id"));
        req.setAttribute("pw", req.getParameter("pw"));

        // 비밀번호 틀릴 경우
        if (exception.getMessage().equals("Bad credentials"))
            req.setAttribute("errorMsg", "로그인에 실패하였습니다!");
        else    // 아이디 틀릴 경우 or  이미 탈퇴된 유저일 경우
            req.setAttribute("errorMsg", exception.getMessage());

        req.getRequestDispatcher("/").forward(req, res);
    }
}