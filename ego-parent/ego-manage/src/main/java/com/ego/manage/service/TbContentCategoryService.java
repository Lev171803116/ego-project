package com.ego.manage.service;

import com.ego.TbContentCategory;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;

import java.util.List;

public interface TbContentCategoryService {

    //查询所有类目，并转换为easyUiTree的属性要求
    List<EasyUiTree> showCategory(long id);

    //类目新增
    EgoResult create(TbContentCategory category);

    //类目重命名
    EgoResult update(TbContentCategory category);

    //删除类目
    EgoResult delete(TbContentCategory category);


}
