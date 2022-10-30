package com.ruoxin.messaging.advice;

import com.ruoxin.messaging.exception.MessagingSystemException;
import com.ruoxin.messaging.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MessagingSystemException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleMessagingSystemException(MessagingSystemException exception) {
        System.out.println(exception);
        BaseResponse responseBody = new BaseResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(responseBody, exception.getHttpStatus());
    }
}
