package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.ego.dubboservice.mapper.TbUserMapper;
import com.ego.dubboservice.service.TbUserDubboService;
import com.ego.TbUser;
import com.ego.TbUserExample;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Resource
    private TbUserMapper tbUserMapper;

    public TbUser selByUser(TbUser user) {

        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(example);

        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }
}
