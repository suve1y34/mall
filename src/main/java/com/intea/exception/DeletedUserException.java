package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DeletedUserException extends RuntimeException {
    public DeletedUserException(String msg) {
        super(msg);
    }
}
