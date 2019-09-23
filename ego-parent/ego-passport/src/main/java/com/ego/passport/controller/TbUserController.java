package com.ego.passport.controller;

import com.alibaba.fastjson.JSON;
import com.ego.TbUser;
import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TbUserController {

    //显示登录页面
    @Resource
    private TbUserService tbUserServiceImpl;
    @RequestMapping("user/showLogin")
    public String showLog(@RequestHeader(value="Referer",defaultValue = "") String url, Model model, String interurl){
       if(null != interurl && !"".equals(interurl)){
           model.addAttribute("redirect",interurl);
       }else if(null != url &&!"".equals(url)){
           model.addAttribute("redirect",url);
       }

        return "login";
    }

    //登录
    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response){
        return tbUserServiceImpl.login(user,request,response);
    }


    //通过token获取用户信息
    @CrossOrigin
    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable String token, String callback){
        EgoResult er = tbUserServiceImpl.getUserInfoByToken(token);
        if(callback!=null && !callback.equals("")){

            return callback+"("+ JSON.toJSONString(er)+")";
        }
        return  er;
    }

    //通过token获取用户信息
    @CrossOrigin
    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public Object logOut(@PathVariable String token,String callback,HttpServletRequest request, HttpServletResponse response){
        EgoResult er = tbUserServiceImpl.logOut(token,request,response);
        if(callback!=null && !callback.equals("")){

            return callback+"("+ JSON.toJSONString(er)+")";
        }
        return  er;
    }
}
