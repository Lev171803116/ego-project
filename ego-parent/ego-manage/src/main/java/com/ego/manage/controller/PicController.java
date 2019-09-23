package com.ego.manage.controller;

import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class PicController {

    @Autowired
    private PicService picServiceImpl;
    /**
     *@描述
     *@参数 [uploadFile]
     *@返回值 java.util.Map<java.lang.String,java.lang.Object>
     *@创建人  Lev
     *@创建时间 2019/8/21
     *@修改人和其它信息
     */
    @RequestMapping("pic/upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile uploadFile){
       return picServiceImpl.upload(uploadFile);
    }
}
