package com.ego.dubboservice.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.TbItem;
import com.ego.TbItemDesc;
import com.ego.TbItemParamItem;


import java.util.List;

/**
     *@描述
     *@参数 [page, rows]
     *@返回值 com.ego.commons.pojo.EasyUIDataGrid
     *@创建人  Lev
     *@创建时间 2019/8/11
     *@修改人和其它信息
     */

    public interface TbItemDubboService {

        //查询页面
        EasyUIDataGrid show(int page, int rows);
        //根据id修改状态
        int updItemStatus(TbItem tbItem);
        //新增
        int insTbItem(TbItem tbItem);

        //新增包含商品表和商品描述表
        int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem tbItemParamItem) throws Exception;

        //通过状态查询全部可用数据
        List<TbItem> selAllByStatus(byte status);

        //根据主键查询
        TbItem selById(long id);
    }
