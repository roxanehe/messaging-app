package com.ruoxin.messaging.enums;

import org.springframework.http.HttpStatus;

public enum Status {
    OK(1000, "Successful", HttpStatus.OK),
    PASSWORD_NOT_MATCHED(1001, "Passwords are not matched", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(1002, "Password is too short", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(1003, "Username already exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1004, "Email already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1005, "User doesn't exist", HttpStatus.BAD_REQUEST),
    VALIDATION_CODE_NOT_EXISTS(1006, "Validation code doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_CODE_IS_WRONG(1007, "Validation code is wrong", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;

    Status(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
