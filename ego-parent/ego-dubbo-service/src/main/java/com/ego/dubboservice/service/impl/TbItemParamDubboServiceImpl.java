package com.ego.dubboservice.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubboservice.mapper.TbItemParamMapper;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.dubboservice.service.TbItemParamDubboService;
import com.ego.TbItemParam;
import com.ego.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


@Service(version = "1.0.0",interfaceClass=TbItemParamDubboService.class)
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Resource
    private TbItemParamMapper tbItemParamMapper;

    public EasyUIDataGrid showPage(int page, int rows) {
        //设置分页条件
        PageHelper.startPage(page,rows);

        //查询全部
        //xxxExample()，相当于在sql中where从句中添加了条件
        //如果使用xxxwithBlobs(),查询结果包含text类的列，如果没有xxxwithBlobs()方法不带text类型
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pi = new PageInfo<TbItemParam>(list);

        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());
        return dataGrid;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delByIds(String ids) throws Exception {
        int index = 0;
        String[] idStr = ids.split(",");
        for (String id : idStr) {
            index +=tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        if(index == idStr.length){
            return 1;
        }else {
            throw new Exception("删除失败.可能原因:数据已经不存在");
        }
    }

    public TbItemParam selectByCatid(long catId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if(list != null && list.size()>0){
            //要求每个类目只能有一个模板
           return list.get(0);
        }
        return null;
    }

    public int insParam(TbItemParam tbItemParam) {
        return tbItemParamMapper.insertSelective(tbItemParam);
    }


}
