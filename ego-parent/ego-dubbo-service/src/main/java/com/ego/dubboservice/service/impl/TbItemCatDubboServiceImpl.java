package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.ego.dubboservice.mapper.TbItemCatMapper;
import com.ego.dubboservice.service.TbItemCatDubboService;
import com.ego.TbItemCat;
import com.ego.TbItemCatExample;

import javax.annotation.Resource;
import java.util.List;

@Service(version = "1.0.0",timeout = 3000)
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {
    @Resource
    private TbItemCatMapper tbItemCatMapper;

    public List<TbItemCat> show(long pid) {
        TbItemCatExample example =new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        return tbItemCatMapper.selectByExample(example);
    }

    public TbItemCat selectById(long id) {

        return tbItemCatMapper.selectByPrimaryKey(id);
    }

}
