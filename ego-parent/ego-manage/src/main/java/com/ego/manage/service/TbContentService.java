package com.ego.manage.service;

import com.ego.TbContent;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;

public interface TbContentService {
    //分页显示
    EasyUIDataGrid showContent(long categoryId, int page, int rows);

    //新增
    EgoResult save(TbContent content);
}
