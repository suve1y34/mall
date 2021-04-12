package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistProductException extends RuntimeException {
    public NotExistProductException(String msg) {
        super(msg);
    }
}
