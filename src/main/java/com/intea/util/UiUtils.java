package com.intea.util;

import com.intea.constant.Method;
import com.intea.paging.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class UiUtils {

    public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
                                          @RequestParam(value = "redirectUri", required = false) String redirectUri,
                                          @RequestParam(value = "method", required = false) Method method,
                                          @RequestParam(value = "param", required = false) Map<String, Object> param, Model model) {

        model.addAttribute("message", message);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("method", method);
        model.addAttribute("param", param);

        return "utils/message-redirect";
    }


    public Map<String, Object> getPagingParams(Criteria cri) {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("currentPageNo", cri.getCurrentPageNo());
        param.put("recordsPerPage", cri.getRecordsPerPage());
        param.put("pageSize", cri.getPageSize());
        param.put("searchKeyword", cri.getSearchKeyword());

        return param;
    }

}
