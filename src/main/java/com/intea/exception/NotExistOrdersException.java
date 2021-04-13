package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistOrdersException extends RuntimeException {
    public NotExistOrdersException(String msg) {
        super(msg);
    }
}
