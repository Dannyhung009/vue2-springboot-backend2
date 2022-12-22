package com.example.exception;


import lombok.Getter;

/**
 * 自定義異常
 */
@Getter
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code = code;
    }

}
