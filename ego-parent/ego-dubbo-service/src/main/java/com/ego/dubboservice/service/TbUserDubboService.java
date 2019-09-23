package com.ego.dubboservice.service;


import com.ego.TbUser;

public interface TbUserDubboService {

    //根据用户名和密码查询用户
    TbUser selByUser(TbUser user);
}
