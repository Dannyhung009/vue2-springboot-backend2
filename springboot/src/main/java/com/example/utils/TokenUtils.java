package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {


    private static IUserService staticUserService;
    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

//    @Autowired
//    private User user;
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成JWT Token
     *
     * @param String userId,String sign
     * @return
     */
    public static String generateToken(String userId, String sign) {
//        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);//生成過期的時間
        String token = "";
        token = JWT.create().withAudience(userId) // 将 user id 保存到 token 里面，作為payload
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2小時後token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
        return token;
    }

    /**
     * 獲取當前登入用戶的訊息
     *
     * @return user object
     */

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
//        if(token != null){
//          String aud = JWT.decode(token).getAudience().get(0);
//        }
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                User user = staticUserService.getById(Integer.valueOf(userId));
                return user;

            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
