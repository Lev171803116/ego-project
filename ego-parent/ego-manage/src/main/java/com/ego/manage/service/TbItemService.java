package com.ego.manage.service;

import com.ego.TbItem;
import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemService {

    /**
     *@描述 显示商品
     *@参数
     *@返回值
     *@创建人  Lev
     *@创建时间 2019/8/14
     *@修改人和其它信息
     */
    EasyUIDataGrid show(int page, int rows);

    //批量修改
    int updItemStatus(String ids, byte status);

    //商品新增
    int save(TbItem item, String desc, String tbItemParamItem) throws Exception;

}
