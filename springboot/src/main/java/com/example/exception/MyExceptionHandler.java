package com.example.exception;

import com.example.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {//統一異常接收器 Universal Exception Handler


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handler(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }


}
