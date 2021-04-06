package com.intea.hanlder;

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

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    // 로그인 성공 시 호출되는 메소드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 관리자로 로그인할 경우
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            String redirectUrl = "/admin/main";

            redirectStrategy.sendRedirect(request, response, redirectUrl);

            return;
        }

        // 일반 유저로 로그인할 경우
        if (session != null) {
            resultRedirectStrategy(request, response, authentication);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    protected void resultRedirectStrategy(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(req, res);

        if(savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(req, res, targetUrl);
        } else {
            redirectStrategy.sendRedirect(req, res, "/");
        }
    }
}