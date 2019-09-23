package com.ego.manage.controller;

import com.ego.TbItemParam;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemParamController {

    @Resource
    private TbItemParamService tbItemParamServiceImpl;


    //规格参数-分页显示
    @RequestMapping("item/param/list")
    @ResponseBody
    public EasyUIDataGrid showPage(int page,int rows){
        return tbItemParamServiceImpl.showPage(page,rows);
    }

    //批量删除规格参数
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult egoResult = new EgoResult();
        try {
            int idex =tbItemParamServiceImpl.delete(ids);
            if(idex == 1){
                egoResult.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            egoResult.setData(e.getMessage());
        }
        return  egoResult;
    }

    //点击商品类目按钮显示添加分组按钮，判断类目是否已经添加模板
    @RequestMapping("item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult show(@PathVariable  long catId){
    return tbItemParamServiceImpl.showParam(catId);
    }


    //商品类目新增
    @RequestMapping("item/param/save/{catId}")
    @ResponseBody
    public EgoResult save(TbItemParam tbItemParam,@PathVariable long catId){
        tbItemParam.setItemCatId(catId);
        return tbItemParamServiceImpl.save(tbItemParam);
    }
}
