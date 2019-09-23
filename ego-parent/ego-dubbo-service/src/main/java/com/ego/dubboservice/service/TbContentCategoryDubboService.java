package com.ego.dubboservice.service;



import com.ego.TbContentCategory;

import java.util.List;


public interface TbContentCategoryDubboService {
    //根据父id查询所有子类目
    List<TbContentCategory> selByPid(long id);

    //新增
    int instbContentCategory(TbContentCategory category);

    //修改
    int updIsParentById(TbContentCategory category);

    //根据id查询内容类目详细信息
    TbContentCategory selById(long id);

}
