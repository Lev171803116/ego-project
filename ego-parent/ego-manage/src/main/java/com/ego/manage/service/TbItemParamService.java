package com.ego.manage.service;

import com.ego.TbItemParam;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;

public interface TbItemParamService {
    EasyUIDataGrid showPage(int rows, int page);

    //批量删除
    int delete(String ids) throws Exception;

    //根据类目查询模板信息
    EgoResult showParam(long catId);

    //新增
    EgoResult save(TbItemParam tbItemParam);
}
