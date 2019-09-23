package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.TbItemParam;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubboservice.service.TbItemCatDubboService;
import com.ego.dubboservice.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParaChild;
import com.ego.manage.service.TbItemParamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Reference(version = "1.0.0")
    private TbItemParamDubboService tbItemParamDubboServiceImpl;

    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public EasyUIDataGrid showPage(int rows, int page) {
        EasyUIDataGrid dataGrid = tbItemParamDubboServiceImpl.showPage(rows, page);
        List<TbItemParam> list = (List<TbItemParam>) dataGrid.getRows();

        List<TbItemParaChild> listChild = new ArrayList<>();
        for (TbItemParam tbItemParam : list) {
            TbItemParaChild tbItemParaChild = new TbItemParaChild();

            tbItemParaChild.setCreated(tbItemParam.getCreated());
            tbItemParaChild.setItemCatName(tbItemCatDubboServiceImpl.selectById(tbItemParam.getItemCatId()).getName());
            tbItemParaChild.setId(tbItemParam.getId());
            tbItemParaChild.setItemCatId(tbItemParam.getItemCatId());
            tbItemParaChild.setParamData(tbItemParam.getParamData());
            tbItemParaChild.setUpdated(tbItemParam.getUpdated());

            listChild.add(tbItemParaChild);
        }
        dataGrid.setRows(listChild);
        return dataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        return tbItemParamDubboServiceImpl.delByIds(ids);
    }

    @Override
    public EgoResult showParam(long catId) {
        EgoResult egoResult = new EgoResult();
        TbItemParam tbItemParam = tbItemParamDubboServiceImpl.selectByCatid(catId);
        if(null != tbItemParam){
            egoResult.setStatus(200);
            egoResult.setData(tbItemParam);
        }
        return egoResult;
    }

    @Override
    public EgoResult save(TbItemParam tbItemParam) {

        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int index=tbItemParamDubboServiceImpl.insParam(tbItemParam);
        EgoResult egoResult = new EgoResult();
        if(index > 0){
         egoResult.setStatus(200);
        }
        return egoResult;
    }

}
