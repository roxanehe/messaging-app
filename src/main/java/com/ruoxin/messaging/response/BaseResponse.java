package com.ruoxin.messaging.response;

import com.ruoxin.messaging.enums.Status;

public class BaseResponse {
    private int code;
    private String message;

    public BaseResponse(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
