package com.yxzh.hall.component;

import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final String[] NOT_CHECK_URL = {"/pad/**","/windows/**"};



    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {


        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 地址过滤
        String url = request.getServletPath() ;
        System.out.println("servletPath = " + url);
       /* boolean isNotCheck = isNotCheck(url);
        if (isNotCheck) {
            return true;
        }*/

        String userid= (String) WebUtils.getSessionAttribute(request, "userid");

         if(StringUtils.isEmpty(userid)){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }

         return true;
    }

    /**
     * 根据URL判断当前请求是否不需要校验, true:false需要校验
     */
    public boolean isNotCheck(String servletPath) {

        servletPath = servletPath.endsWith("/")
                ? servletPath.substring(0,servletPath.lastIndexOf("/"))
                : servletPath;
        System.out.println("servletPath = " + servletPath);

        for (String path : NOT_CHECK_URL) {

            if (servletPath.equals(path)) {
                return true;
            }

            // path 以 /** 结尾, servletPath 以 path 前缀开头
            if (path.endsWith("/**")) {
                String pathStart = path.substring(0, path.lastIndexOf("/")+1);
               if (servletPath.startsWith(pathStart)) {
                    return true;
                }
                String pathSub = path.substring(0, path.lastIndexOf("/"));
                if (servletPath.equals(pathSub)) {
                    return true;
                }
            }


        }
        return false;
    }

}
