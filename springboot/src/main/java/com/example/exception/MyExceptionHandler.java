package com.example.exception;

import com.example.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handler(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }


}
