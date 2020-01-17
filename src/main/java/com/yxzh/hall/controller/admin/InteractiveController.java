package com.yxzh.hall.controller.admin;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

@Controller
public class InteractiveController {

    @Value("${upload.externalImagesDir}")
    private String externalImagesDir;

    @GetMapping(value="/admin/advertAdd")
    public String advertAdd()
    {
        return "/admin/ImagesAdd.html";
    }

    @PostMapping("/admin/uploadimg")
    @ResponseBody
    public JSONObject uploadimg(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        System.out.println(title);
        JSONObject res = new JSONObject();

        String filename="";
        String filenames="";
        if(!file.isEmpty())
        {
            filename = UUID.randomUUID().toString().replaceAll("-", "");
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            filenames = filename + "." + ext;
            String pathname = externalImagesDir+ filenames;
            System.out.println(pathname);
            file.transferTo(new File(pathname));

        }

        res.put("msg", "");
        res.put("code", 0);
        res.put("data", filenames);

        return res;
    }


}
