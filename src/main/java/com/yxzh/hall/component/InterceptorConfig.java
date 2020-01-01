package com.yxzh.hall.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/login","/login.html","/pad/**","/images/**","/scripts/**","/styles/**","/layui/**","/error");
    }
}
