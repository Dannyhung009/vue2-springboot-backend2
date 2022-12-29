package com.example.config;

import com.example.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {//攔截器，負責攔截所有請求，只讓有登入的請求通過


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())//使用 jwtInterceptor() 攔截器
                .addPathPatterns("/**") // 攔截所有請求，通過判斷是否有 @LoginRequired 註解 決定是否需要登錄
                .excludePathPatterns("/user/login",
                        "/user/register",
                        "/user/export",
                        "/user/import",
                        "/file/upload",
                        "/file/**"
                );//例外接口不需要驗證登入



    }
    @Bean
    public JwtInterceptor jwtInterceptor(){//使用@Bean將jwtInterceptor()注入到spring
        return new JwtInterceptor();
    }

}
