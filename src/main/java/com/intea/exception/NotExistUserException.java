package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistUserException extends RuntimeException {

    public NotExistUserException(String msg) {
        super(msg);
    }
}
