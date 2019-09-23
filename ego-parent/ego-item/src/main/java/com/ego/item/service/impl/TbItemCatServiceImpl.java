package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.TbItemCat;

import com.ego.dubboservice.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Reference(version = "1.0.0")
   private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Override
    public PortalMenu showCatMenu() {
        //查询出所有一级菜单
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
        PortalMenu pm = new PortalMenu();
        pm.setData(selAllMenu(list));
        return pm;

    }

    public List<Object> selAllMenu(List<TbItemCat> list){
        List<Object> listNode = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            if (tbItemCat.getIsParent()) {
                PortalMenuNode pmd = new PortalMenuNode();
                pmd.setU("/products/" + tbItemCat.getId() + ".html");
                pmd.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                pmd.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
                listNode.add(pmd);
            }else{
                listNode.add("/products/" + tbItemCat.getId() + ".html|"+tbItemCat.getName());
            }
        }
        return listNode;
    }
}
