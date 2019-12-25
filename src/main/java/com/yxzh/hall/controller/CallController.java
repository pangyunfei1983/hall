package com.yxzh.hall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxzh.hall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CallController {

    @Autowired
    private CallInfoService service;

    @RequestMapping("init")
    public String init(String windowid,String floor, Model model) {
        model.addAttribute("windowid", windowid);
        model.addAttribute("floor",floor);

        return "x9";
    }

    @RequestMapping(value = {"initNetwork"})
    @ResponseBody
    public String initNetwork(@RequestBody(required=false) String json)
    {
        if (json!=null && !json.isEmpty()) {
            JSONObject jo = JSON.parseObject(json);
            String clientIp = jo.getString("clientIp");
            String clientMac=jo.getString("clientMac");
            String floor=jo.getString("floor");
            String windowid=jo.getString("windowid");
        }
        JSONObject obj = new JSONObject();
        obj.put("StatusCode", "200");
        return obj.toJSONString();
    }

    @PostMapping(value = {"x9"})
    @ResponseBody
    public String x9(@Valid @RequestBody String json) {
        if (json!=null && !json.isEmpty()) {
            JSONObject jo = JSON.parseObject(json);
            String action = jo.getString("action");
            if (action.equals("getTime")) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                JSONObject obj = new JSONObject();
                obj.put("now", df.format(new Date()));
                return obj.toJSONString();

            } else if (action.equals("getWait")) {

                String windowid= jo.getString("windowid");
                String floor= jo.getString("floor");
                int total= service.GetWait(windowid,floor);
                JSONObject obj = new JSONObject();
                obj.put("wait", total);
                System.out.println(obj.toJSONString());
                return obj.toJSONString();

            } else if (action.equals("doCall")) {

                String windowid= jo.getString("windowid");
                String floor= jo.getString("floor");
                return  service.DoCall(windowid,floor);
            }

        }

        return "";
    }



}
