package com.ego.dubboservice.service;


import com.ego.TbItemDesc;

public interface TbItemDescDubboService {

    //新增
    int insDesc(TbItemDesc itemDesc);

    //根据id查询全部
    TbItemDesc selById(long itemId);
}
