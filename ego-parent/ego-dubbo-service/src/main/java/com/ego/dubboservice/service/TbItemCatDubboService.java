package com.ego.dubboservice.service;



import com.ego.TbItemCat;

import java.util.List;

public interface TbItemCatDubboService {

    //根据父目录id,查询所有子目录
    List<TbItemCat> show(long pid);

    //根据类目id查询
    TbItemCat selectById(long id);

}
