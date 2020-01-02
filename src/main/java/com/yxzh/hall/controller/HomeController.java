package com.yxzh.hall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {


    @GetMapping(value="/login")
    public String login()
    {
        return "login";
    }

    @PostMapping(value="/login")
    public String login (@RequestParam("userid") String userid,
                         @RequestParam("password") String password,HttpServletRequest request, HttpServletResponse response ){


        if (!StringUtils.isEmpty(userid)) {
            WebUtils.setSessionAttribute(request,"userid",userid);
        }

        return "index" ;
    }

}
