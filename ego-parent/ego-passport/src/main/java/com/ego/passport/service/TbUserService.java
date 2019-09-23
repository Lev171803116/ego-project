package com.ego.passport.service;

import com.ego.TbUser;
import com.ego.commons.pojo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TbUserService {

    //登录
    EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    //根据token查询用户信息
    EgoResult getUserInfoByToken(String token);

    //退出
    EgoResult logOut(String token, HttpServletRequest request, HttpServletResponse response);
}
