package com.ego.manage.controller;

import com.ego.TbItem;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;

    @RequestMapping("item/list")
    @ResponseBody
    public EasyUIDataGrid show(int page, int rows){
        return tbItemServiceImpl.show(page,rows);
    }

    @RequestMapping("rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }


    //商品删除
    @RequestMapping("rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids,(byte)3);
        if(index == 1) {
            egoResult.setStatus(200);
        }
        return  egoResult;
    }

    //商品上架
    @RequestMapping("rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids,(byte)1);
        if(index == 1) {
            egoResult.setStatus(200);
        }
        return  egoResult;
    }

    //商品下架
    @RequestMapping("rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids,(byte)2);
        if(index == 1) {
            egoResult.setStatus(200);
        }
        return  egoResult;
    }

    //商品新增
    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult insert(TbItem item,String desc,String itemParams){
        EgoResult egoResult = new EgoResult();
        int index = 0;
        try {
            index = tbItemServiceImpl.save(item, desc,itemParams);
            if(index == 1){
                egoResult.setStatus(200);
            }
        } catch (Exception e) {
            egoResult.setData(e.getMessage());
        }
        return  egoResult;
    }
}
