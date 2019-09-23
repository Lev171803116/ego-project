package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

public class TbItemDescController {
    @Resource
    private TbItemDescService tbItemDescServiceImpl;


    //显示商品详情
    @RequestMapping(value = "item/desc/{id}.html",produces = "text/html,charset=utf-8")
    @ResponseBody
    public String desc(@PathVariable long id){

        return tbItemDescServiceImpl.showDesc(id);
    }
}
