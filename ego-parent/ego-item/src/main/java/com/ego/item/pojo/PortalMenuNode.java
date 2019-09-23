package com.ego.item.pojo;

import java.util.List;

/**
 *@描述 portal最终要的数据格式
 *@参数
 *@返回值
 *@创建人  Lev
 *@创建时间 2019/8/25
 *@修改人和其它信息
 */
public class PortalMenuNode {
    private String u;
    private String n;
    private List<Object> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<Object> getI() {
        return i;
    }

    public void setI(List<Object> i) {
        this.i = i;
    }
}
