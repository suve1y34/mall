package com.intea.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model) {
        return "error/404";
    }
}
