package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.TbUser;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;

import com.ego.dubboservice.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;

    @Resource
    private JedisDao jedisDaoImpl;
    @Override
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        TbUser userSelect = tbUserDubboServiceImpl.selByUser(user);

        if(userSelect != null){
            er.setStatus(200);
            //当用户登录成功后把用户信息放入到redis中

            String key= UUID.randomUUID().toString();
            jedisDaoImpl.set(key, JSON.toJSONString(userSelect));
            jedisDaoImpl.expire(key,60*60*24);
            //产生一个cookie
            CookieUtils.setCookie(request,response,"TT_TOKEN",key,60*60*24);
        }else{
            er.setMsg("用户名和密码错误");
        }
        return er;
    }

    @Override
    public EgoResult getUserInfoByToken(String token) {
        EgoResult er = new EgoResult();
        String json = jedisDaoImpl.get(token);
        if(json !=null && !json.equals("")){
            TbUser tbUser = JSONObject.parseObject(json, TbUser.class);

            //把密码清空
            tbUser.setPassword(null);
            er.setStatus(200);
            er.setMsg("OK");
            er.setData(tbUser);
        }else {
            er.setMsg("获取失败");
        }


        return er;
    }

    @Override
    public EgoResult logOut(String token, HttpServletRequest request, HttpServletResponse response) {
        jedisDaoImpl.del(token);
        CookieUtils.deleteCookie(request,response,"TT_TOKEN");
        EgoResult er = new EgoResult();
        er.setMsg("OK");
        er.setStatus(200);
        return er;
    }
}
