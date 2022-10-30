package com.ruoxin.messaging.exception;

import com.ruoxin.messaging.enums.Status;
import org.springframework.http.HttpStatus;

public class MessagingSystemException extends Exception {
    private int code; // 1000, 1001, 1002
    private String message;
    private HttpStatus httpStatus;

    public MessagingSystemException(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.httpStatus = status.getHttpStatus();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
