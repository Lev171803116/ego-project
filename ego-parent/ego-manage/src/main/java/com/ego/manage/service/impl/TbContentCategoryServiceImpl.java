package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.TbContentCategory;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;

import com.ego.dubboservice.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

    @Override
    public List<EasyUiTree> showCategory(long id) {
        ArrayList<EasyUiTree> listTree = new ArrayList<>();
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
        for (TbContentCategory category : list) {
            EasyUiTree tree = new EasyUiTree();
            tree.setId(category.getId());
            tree.setText(category.getName());
            tree.setState(category.getIsParent()? "closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }

    @Override
    public EgoResult create(TbContentCategory category) {
        EgoResult egoResult = new EgoResult();
        //判断当前节点名称是否已经存在
        List<TbContentCategory> childen = tbContentCategoryDubboServiceImpl.selByPid(category.getParentId());
        for (TbContentCategory chil : childen) {
            if (chil.getName().equals(category.getName())){
                egoResult.setData("该分类名称已存在");
                return  egoResult;
            }
        }
        
        Date date = new Date();
        category.setCreated(date);
        category.setUpdated(date);
        category.setIsParent(false);
        category.setStatus(1);
        category.setSortOrder(1);
        long id = IDUtils.genItemId();
        category.setId(id);
        int index =tbContentCategoryDubboServiceImpl.instbContentCategory(category);
        if(index >0){
            TbContentCategory parent = new TbContentCategory();
            parent.setId(category.getParentId());
            parent.setIsParent(true);
            tbContentCategoryDubboServiceImpl.updIsParentById(parent);
        }

        HashMap<String, Long> map = new HashMap<>();
        map.put("id",id);
        egoResult.setStatus(200);
        egoResult.setData(map);
        return egoResult;

    }

    @Override
    public EgoResult update(TbContentCategory category) {
        EgoResult egoResult = new EgoResult();
        //查询当前节点信息
        TbContentCategory cateSelect = tbContentCategoryDubboServiceImpl.selById(category.getId());
        //查询当前节点的父节点的所有子节点的信息
        List<TbContentCategory> childen = tbContentCategoryDubboServiceImpl.selByPid(cateSelect.getParentId());
        for (TbContentCategory chil : childen) {
            if (chil.getName().equals(category.getName())){
                egoResult.setData("该分类名称已存在");
                return  egoResult;
            }
        }
        int index = tbContentCategoryDubboServiceImpl.updIsParentById(category);
        if(index > 0){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

    @Override
    public EgoResult delete(TbContentCategory category) {
        EgoResult egoResult = new EgoResult();
        category.setStatus(0);
        int index = tbContentCategoryDubboServiceImpl.updIsParentById(category);
        if(index > 0){
            TbContentCategory current = tbContentCategoryDubboServiceImpl.selById(category.getId());
            List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(current.getParentId());

            if(null == list ||list.size() == 0){
                TbContentCategory parent = new TbContentCategory();
                parent.setId(current.getParentId());
                parent.setIsParent(false);
                int result = tbContentCategoryDubboServiceImpl.updIsParentById(parent);
                if (result > 0){
                    egoResult.setStatus(200);
                }
            }else {
                egoResult.setStatus(200);
            }
        }
        return egoResult;
    }
}
