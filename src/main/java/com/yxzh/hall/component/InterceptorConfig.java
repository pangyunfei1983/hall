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

        List<String> excludePath = new ArrayList<>();
        excludePath.add("/user_register");
        excludePath.add("/login");
        excludePath.add("/logout");
        excludePath.add("/static/**");
        excludePath.add("/assets/**");
        excludePath.add("/templates/**");
        excludePath.add("/public/**");

        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
    }
}
