package com.yxzh.hall.service;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yxzh.hall.mapper.DisplayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayService {

    private final Logger log = LoggerFactory.getLogger(DisplayService.class);

    @Autowired
    private DisplayMapper displayMapper;

    public String GetWindowInfo(String windowid)
    {
        JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd";//设置日期格式
        String json= JSON.toJSONString(displayMapper.GetWindowInfo(windowid), SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.DisableCircularReferenceDetect);
         log.debug(json);
         return json;
    }


}
