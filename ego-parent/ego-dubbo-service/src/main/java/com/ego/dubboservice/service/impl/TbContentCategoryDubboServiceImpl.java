package com.ego.dubboservice.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubboservice.mapper.TbContentCategoryMapper;
import com.ego.dubboservice.service.TbContentCategoryDubboService;
import com.ego.TbContentCategory;
import com.ego.TbContentCategoryExample;


import javax.annotation.Resource;
import java.util.List;


@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    public List<TbContentCategory> selByPid(long id) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }

    public int instbContentCategory(TbContentCategory category) {
        return tbContentCategoryMapper.insertSelective(category);
    }

    public int updIsParentById(TbContentCategory category) {
        return  tbContentCategoryMapper.updateByPrimaryKeySelective(category);

    }

    public TbContentCategory selById(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }
}
