package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubboservice.mapper.TbItemParamItemMapper;
import com.ego.dubboservice.service.TbItemParamItemDubboService;
import com.ego.TbItemParamItem;
import com.ego.TbItemParamItemExample;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    public TbItemParamItem selByItemId(long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list!= null && list.size()>0 ){
            return list.get(0);
        }
        return null;
    }
}
