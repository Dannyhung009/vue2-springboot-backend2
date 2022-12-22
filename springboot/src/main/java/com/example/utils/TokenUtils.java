package com.example.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TokenUtils {

//    @Autowired
//    private User user;
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     *生成JWT Token
     * @param User user
     * @return
     */
    public static String generateToken(String userId,String sign){
//        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);//生成過期的時間
        String token="";
        token= JWT.create().withAudience(userId) // 将 user id 保存到 token 里面，作為payload
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小時後token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
        return token;
    }
}
