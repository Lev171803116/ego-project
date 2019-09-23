package com.ego.dubboservice.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.TbContent;

import java.util.List;

public interface TbContentDubboService {

    //分页查询
    EasyUIDataGrid selContentByPage(long categoryId, int page, int rows);

    //新增
    int insContent(TbContent content);

    //查询出最近的前N个
    List<TbContent> selByCount(int count, boolean isSort);
}
