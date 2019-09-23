package com.ego.dubboservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubboservice.mapper.TbOrderItemMapper;
import com.ego.dubboservice.mapper.TbOrderMapper;
import com.ego.dubboservice.mapper.TbOrderShippingMapper;
import com.ego.dubboservice.service.TbOrderDubboService;
import com.ego.TbOrder;
import com.ego.TbOrderItem;
import com.ego.TbOrderShipping;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;
    public int isrOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception {
        int index = tbOrderMapper.insertSelective(order);
        for (TbOrderItem tbOrderItem : list) {
            index+=tbOrderItemMapper.insertSelective(tbOrderItem);
        }
        index+=tbOrderShippingMapper.insertSelective(shipping);

        if(index == 2+list.size()){
            return  1;
        }else {
            throw new Exception("创建订单失败");
        }

    }
}
