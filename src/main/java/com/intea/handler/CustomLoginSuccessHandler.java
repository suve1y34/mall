package com.intea.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache reqCache = new HttpSessionRequestCache();
    private RedirectStrategy rdStrategy = new DefaultRedirectStrategy();

    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    // 로그인 성공 시 호출되는 메소드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
        HttpSession hs = req.getSession();

        // 관리자로 로그인할 경우
        if (auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            String redirectUrl = "/admin";

            getRedirectStrategy().sendRedirect(req, res, redirectUrl);

            return;
        }

        // 일반 유저로 로그인할 경우
        if (hs != null) {
            resultRedirectStrategy(req, res, auth);
        } else {
            super.onAuthenticationSuccess(req, res, auth);
        }
    }

    protected void resultRedirectStrategy(HttpServletRequest req, HttpServletResponse res,
                                          Authentication auth) throws IOException, ServletException {
        SavedRequest savedRequest = reqCache.getRequest(req, res);

        if(savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            rdStrategy.sendRedirect(req, res, targetUrl);
        } else {
            rdStrategy.sendRedirect(req, res, "/");
        }
    }
}