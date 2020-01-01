package com.yxzh.hall.component;

import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final String[] NOT_CHECK_URL = {"/pad/**","/windows/**", "/login"};

    @Resource
    private JwtConfig jwtConfig ;

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

        boolean isNotCheck = isNotCheck(url);
        if (isNotCheck) {
            return true;
        }

/*
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try{
            JSONObject json = new JSONObject();
            json.put("success","false");
            json.put("msg","认证失败，未通过拦截器");
            json.put("code","50000");
            response.getWriter().append(json.toJSONString());
            System.out.println("认证失败，未通过拦截器");
            //        response.getWriter().write("50000");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
*/
        // Token 验证
        String token = request.getHeader(jwtConfig.getHeader());
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }
        if(StringUtils.isEmpty(token)){

            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        Claims claims = jwtConfig.getTokenClaim(token);
        if(claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        //设置 identityId 用户身份ID
        request.setAttribute("identityId", claims.getSubject());
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
