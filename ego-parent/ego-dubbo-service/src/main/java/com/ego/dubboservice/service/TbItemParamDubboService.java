package com.ego.dubboservice.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.TbItemParam;

public interface TbItemParamDubboService {

    //分页查询数据，包含当前页显示数据和总条数
    EasyUIDataGrid showPage(int page, int rows);

    //批量删除
    int delByIds(String ids) throws Exception;

    //根据类目id 查询参数模板
    TbItemParam selectByCatid(long catId);

    //新增参数模板,支持主键自增
    int insParam(TbItemParam tbItemParam);
}
