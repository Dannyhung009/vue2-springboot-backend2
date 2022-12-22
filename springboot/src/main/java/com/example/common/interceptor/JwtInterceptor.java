package com.example.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.common.Constants;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
//        if (token == null) {
//            throw new RuntimeException("无token，请重新登录");
//        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"token驗證失敗");
        }
        //根據token中的userid查詢資料庫
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }



}
