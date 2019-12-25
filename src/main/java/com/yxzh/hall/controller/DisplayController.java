package com.yxzh.hall.controller;

import com.yxzh.hall.service.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DisplayController {

    @Autowired
    DisplayService displayService;

    @RequestMapping(value = {"display"})
    @ResponseBody
    public String GetWindowInfo(String windowid)
    {
        return displayService.GetWindowInfo(windowid);
    }

}
