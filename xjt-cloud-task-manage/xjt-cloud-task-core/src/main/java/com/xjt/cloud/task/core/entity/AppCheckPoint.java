package com.xjt.cloud.task.core.entity;

/**
 * @ClassName AppCheckPoint
 * @Author dwt
 * @Date 2020-08-26 11:28
 * @Version 1.0
 */
public class AppCheckPoint {
    //巡更点id
    private Long c;
    //建筑物名称
    private String bn;
    //楼层名称
    private String fn;
    //二维码
    private String q;
    //巡查点名称
    private String pn;
    private Long t;
    private Long b;
    private Long f;
    private Long s;
    private String sn;
    private String tn;


    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public Long getF() {
        return f;
    }

    public void setF(Long f) {
        this.f = f;
    }

    public Long getS() {
        return s;
    }

    public void setS(Long s) {
        this.s = s;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}
