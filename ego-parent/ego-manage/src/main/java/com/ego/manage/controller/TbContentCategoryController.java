package com.ego.manage.controller;

import com.ego.TbContentCategory;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TbContentCategoryController {

    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;


    //查询商品类目
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUiTree> showCategory(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.showCategory(id);

    }

    //新增商品类目
    @RequestMapping("content/category/create")
    @ResponseBody
    public EgoResult create(TbContentCategory category){
        return  tbContentCategoryServiceImpl.create(category);
    }

    //修改类目
    @RequestMapping("content/category/update")
    @ResponseBody
    public EgoResult update(TbContentCategory category){
        return  tbContentCategoryServiceImpl.update(category);
    }

    //删除类目
    @RequestMapping("content/category/delete")
    @ResponseBody
    public EgoResult delete(TbContentCategory category){
        return  tbContentCategoryServiceImpl.delete(category);
    }
}
