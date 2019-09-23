package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ego.TbItemParamItem;
import com.ego.commons.pojo.ParamItem;
import com.ego.dubboservice.service.TbItemParamItemDubboService;
import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
    @Override
    public String showParam(long itemId) {
        TbItemParamItem item = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
        List<ParamItem> list = JSONObject.parseArray(item.getParamData(), ParamItem.class);
        StringBuilder sb = new StringBuilder();
        for (ParamItem param : list) {
            sb.append("<table width='500' style='color:gray;'>");
            for (int i = 0 ;i<param.getParams().size();i++) {
                if(i==0){
                    sb.append("<tr>");
                    sb.append("<td align='right' width='30%'>"+param.getGroup()+"</td>");
                    sb.append("<td align='right' width='30%'>"+param.getParams().get(i).getK()+"</td>");
                    sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sb.append("<tr/>");
                }else{
                    sb.append("<tr>");
                    sb.append("<td> </td>");
                    sb.append("<td align='right'>"+param.getParams().get(i).getK()+"</td>");
                    sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sb.append("</tr>");
                }
            }
            sb.append("</table>");
            sb.append("<hr style='color:gray;'/>");
        }
        return sb.toString();
    }
}
