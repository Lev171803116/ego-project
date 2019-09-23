package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.ego.dubboservice.mapper.TbItemDescMapper;
import com.ego.dubboservice.service.TbItemDescDubboService;
import com.ego.TbItemDesc;

import javax.annotation.Resource;


@Service(version = "1.0.0",timeout = 3000)
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    //新增
    public int insDesc(TbItemDesc itemDesc) {
        return tbItemDescMapper.insert(itemDesc);
    }

    public TbItemDesc selById(long itemId) {
        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }
}
