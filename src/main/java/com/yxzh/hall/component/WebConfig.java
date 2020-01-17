package com.yxzh.hall.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor ;

    @Value("${upload.externalImagesPath}")
    private String externalImagesPath;



    String[] addPathPatters={"/**" };

    String[] excludePathPatters={"/", "/login/login", "/login/loginPage","/login/register", "/**/*.css", "/**/*.js",
            "/**/*.png", "/**/*.jpg","/**/*.jpeg", "/**/*.gif", "/**/fonts/*",
            "/**/*.svg", "/**/*.ttf"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/druid/**","/login","/login.html","/pad/**","/upload/**","/images/**","/scripts/**","/styles/**","/layui/**","/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/**").addResourceLocations(externalImagesPath);
    }
}
