package com.intea.interceptor;

import com.intea.domain.dto.MembersDTO;
import com.intea.util.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
        HttpSession hs = req.getSession();
        MembersDTO member = SecurityUtils.getLoginMem(hs);

        /*if(member == null) {
            res.sendRedirect("/main");
            return false;
        }*/
        return true;
    }
}
