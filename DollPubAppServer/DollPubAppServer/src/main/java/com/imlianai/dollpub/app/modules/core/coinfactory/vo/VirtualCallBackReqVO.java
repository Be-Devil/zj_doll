package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

/**
 * 虚拟推币机回调请求
 * @author wurui
 * @create 2018-07-03 10:07
 **/
public class VirtualCallBackReqVO extends BaseSignReqVO{
    private int busId;

    // t+z+y=p1+p2=98
    private String p1;
    private String p2;

    // y+sign=36
    private String p3;

    // 水果机数量
    private int p4;

    // 差值
    private int p5;

    // 推板范围
    private int p6;
    private int p7;

    // 回收范围
    private int p8;
    private int p9;

    //金币范围
    private int p10;
    private int p11;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public int getP4() {
        return p4;
    }

    public void setP4(int p4) {
        this.p4 = p4;
    }

    public int getP5() {
        return p5;
    }

    public void setP5(int p5) {
        this.p5 = p5;
    }

    public int getP6() {
        return p6;
    }

    public void setP6(int p6) {
        this.p6 = p6;
    }

    public int getP7() {
        return p7;
    }

    public void setP7(int p7) {
        this.p7 = p7;
    }

    public int getP8() {
        return p8;
    }

    public void setP8(int p8) {
        this.p8 = p8;
    }

    public int getP9() {
        return p9;
    }

    public void setP9(int p9) {
        this.p9 = p9;
    }

    public int getP10() {
        return p10;
    }

    public void setP10(int p10) {
        this.p10 = p10;
    }

    public int getP11() {
        return p11;
    }

    public void setP11(int p11) {
        this.p11 = p11;
    }
}
