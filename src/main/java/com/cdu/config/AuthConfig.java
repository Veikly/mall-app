package com.cdu.config;

import com.cdu.interceptor.AuthInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: cdu-app
 * @Titile: AuthConfig
 * @Author: Administrator
 * @Description: 注册身份拦截器
 */
@SpringBootConfiguration
public class AuthConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**")//所有的url都拦截
                .excludePathPatterns("/api/user/login", "/api/user/reg");//放行
    }
}