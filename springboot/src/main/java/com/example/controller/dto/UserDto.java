package com.example.controller.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收前端登入請求的參數
 */
@Data
public class UserDto implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
