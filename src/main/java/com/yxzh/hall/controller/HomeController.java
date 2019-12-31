package com.yxzh.hall.controller;

import com.yxzh.hall.component.JwtConfig;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Resource
    private JwtConfig jwtConfig ;

    @GetMapping(value="/login")
    public String login()
    {
        return "login";
    }

    @PostMapping(value="/login")
    @ResponseBody
    public Map<String,String> login (@RequestParam("userName") String userName,
                                     @RequestParam("passWord") String passWord){
        Map<String,String> map = new HashMap<>() ;

        String token = jwtConfig.getToken(userName) ;
        if (!StringUtils.isEmpty(token)) {
            map.put("token",token) ;
            map.put("code", "200");
            map.put("userName",userName) ;
        }
        else
        {
            map.put("token","") ;
            map.put("code", "0");
            map.put("userName","") ;
        }

        return map ;
    }

}
