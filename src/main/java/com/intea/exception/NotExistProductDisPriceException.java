package com.intea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistProductDisPriceException extends RuntimeException {
    public NotExistProductDisPriceException(String message) {
        super(message);
    }
}