package com.ego.dubboservice.service.impl;




import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubboservice.mapper.TbItemDescMapper;
import com.ego.dubboservice.mapper.TbItemMapper;
import com.ego.dubboservice.mapper.TbItemParamItemMapper;
import com.ego.dubboservice.service.TbItemDubboService;
import com.ego.TbItem;
import com.ego.TbItemDesc;
import com.ego.TbItemExample;
import com.ego.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


@Service(version = "1.0.0",interfaceClass=TbItemDubboService.class,timeout = 3000)
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Resource
    private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;


    public EasyUIDataGrid show(int page, int rows) {
        PageHelper.startPage(page,rows);
        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());

        //分页代码
        //设置分页条件

        PageInfo<TbItem> pi = new PageInfo<TbItem>(list);

        //放入到实体类
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());
        return  dataGrid;
    }

    public int updItemStatus(TbItem tbItem) {
        //updateByPrimaryKey()传来什么更新什么，而updateByPrimaryKeySelective,传来为null不做操作
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    //TbItem新增
    public int insTbItem(TbItem tbItem) {
        return tbItemMapper.insert(tbItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem tbItemParamItem) throws Exception {
        int index = 0;
        try {
            index = tbItemMapper.insertSelective(tbItem);
            index +=tbItemDescMapper.insertSelective(desc);
            index +=tbItemParamItemMapper.insertSelective(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(index == 3){
            return 1;
        }else {
            throw new Exception("新增失败");
        }

    }

    public List<TbItem> selAllByStatus(byte status) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(example);
    }

    public TbItem selById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

}
