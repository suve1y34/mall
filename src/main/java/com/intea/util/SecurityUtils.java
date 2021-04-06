package com.intea.util;

import com.intea.constant.Const;
import com.intea.domain.dto.MembersDTO;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SecurityUtils {
/*    public static Long getLoginMemPk(HttpServletRequest req) {
        return getLoginMemPk(req.getSession());
    }

    public static Long getLoginMemPk(HttpSession hs) {
        MembersDTO loginMem = (MembersDTO)hs.getAttribute(Const.LOGIN_USER);
        return loginMem == null ? 0 : loginMem.getI_mem();
    }

    public static MembersDTO getLoginMem(HttpServletRequest req) {
        HttpSession hs = req.getSession();
        return (MembersDTO)hs.getAttribute(Const.LOGIN_USER);
    }

    public static MembersDTO getLoginMem(HttpSession hs) {
        return (MembersDTO)hs.getAttribute(Const.LOGIN_USER);
    }

    public static void isLogin(HttpSession hs, HttpServletResponse res
            , HttpServletRequest req) {
        Long loginMem = SecurityUtils.getLoginMemPk(req);
        if(loginMem == Const.FAIL) {
            try {res.sendRedirect("/user/login");}
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public static boolean isLogout(HttpServletRequest req) {
        return getLoginMem(req) == null;
    }

    public String getHashPw(String pw, String cryptPw) {
        return BCrypt.hashpw(pw, cryptPw);
    }*/
}
