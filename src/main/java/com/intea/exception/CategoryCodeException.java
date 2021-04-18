package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CategoryCodeException extends RuntimeException {
    public CategoryCodeException(String msg) {
        super(msg);
    }
}
