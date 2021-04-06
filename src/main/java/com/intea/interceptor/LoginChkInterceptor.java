package com.intea.interceptor;

import com.intea.domain.dto.MembersDTO;
import com.intea.util.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginChkInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object Hanlder) throws Exception {
        String uri = req.getRequestURI();
        String[] uriArr = uri.split("/");

        if(uri.equals("/")) {
            return true;
        }

        System.out.println("인터셉터!");
        boolean isLogout = SecurityUtils.isLogout(req);

        HttpSession hs = req.getSession();
        MembersDTO member = SecurityUtils.getLoginMem(hs);

/*        if(member == null) {
            res.sendRedirect("/user/signin");
            return false;
        }
        if(member.getVerify() != Const.ADMIN) {
            res.sendRedirect("/main");
            return false;
        }*/

        switch(uriArr[1]) {
            case "user":
                switch (uriArr[2]) {
                    case "signin": case "signup":
                        if(!isLogout) {
                            res.sendRedirect("/");
                            return false;
                        }
                    }
        }
        return true;
    }
}
