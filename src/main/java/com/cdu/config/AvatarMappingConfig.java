package com.cdu.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: cdu-app
 * @Titile: AvatarMappingConfig
 * @Author: Administrator
 * @Description: 头像资源映射注册
 */
@SpringBootConfiguration
public class AvatarMappingConfig implements WebMvcConfigurer {
    @Setter
    @Value("${avatar.save.path}")
    private String avatarDir;

    //资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // localhost:8080/avatar/123.png
        registry.addResourceHandler("/avatar/**") //映射的前置url
                .addResourceLocations("file:///" + avatarDir);//定位的资源目录
    }
}