package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubboservice.mapper.TbContentMapper;
import com.ego.dubboservice.service.TbContentDubboService;
import com.ego.TbContent;
import com.ego.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Resource
    private TbContentMapper tbContentMapper;

    public EasyUIDataGrid selContentByPage(long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);
        TbContentExample tbContentExample = new TbContentExample();
        if (categoryId != 0) {
            tbContentExample.createCriteria().andCategoryIdEqualTo(categoryId);

        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
        PageInfo<TbContent> pi = new PageInfo<TbContent>(list);
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());
        return dataGrid;
    }

    public int insContent(TbContent content) {
        return tbContentMapper.insertSelective(content);
    }

    public List<TbContent> selByCount(int count, boolean isSort) {

        TbContentExample example = new TbContentExample();
        if (isSort) {
            example.setOrderByClause("updated desc");
        }
        if (count != 0) {
            PageHelper.startPage(1, count);
            List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
            PageInfo<TbContent> pi = new PageInfo<TbContent>(list);
            return pi.getList();
        } else {
            return tbContentMapper.selectByExampleWithBLOBs(example);
        }
    }
}
