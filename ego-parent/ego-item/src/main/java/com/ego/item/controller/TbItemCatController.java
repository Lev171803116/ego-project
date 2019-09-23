package com.ego.item.controller;


import com.alibaba.fastjson.JSON;
import com.ego.item.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;


@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    @CrossOrigin
    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public String showMenu(@RequestParam(defaultValue = "callback") String callback) throws IOException {
            //不知道为什么springboot使用不了这个方法
//        MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.showCatMenu());
//        mjv.setJsonpFunction(callback);
        String portalMenu= JSON.toJSONString(tbItemCatServiceImpl.showCatMenu());
        return callback+"("+portalMenu+")";
    }
}