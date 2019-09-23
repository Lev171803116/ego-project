package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.TbContent;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;

import com.ego.dubboservice.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Override
    public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.selContentByPage(categoryId,page,rows);
    }

    @Override
    public EgoResult save(TbContent content) {
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        EgoResult egoResult = new EgoResult();
        int index =tbContentDubboServiceImpl.insContent(content);
        if (index >0){
            egoResult.setStatus(200);
        }
        return egoResult;
    }
}
