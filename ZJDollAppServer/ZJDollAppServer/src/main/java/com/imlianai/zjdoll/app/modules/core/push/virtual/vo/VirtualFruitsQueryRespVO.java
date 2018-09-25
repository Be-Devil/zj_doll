package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

/**
 * 水果机查询响应
 * @author wurui
 * @create 2018-07-21 15:16
 **/
public class VirtualFruitsQueryRespVO extends BaseRespVO {

    private int busId;

    //组合
    private int a;
    private int b;
    private int c;

    //分配id
    private long allotId;

    //中奖id
    private int fvId;

    //中奖类型
    private int type;
    //中奖金币
    private int coin;
    //倍率(转盘游戏会有倍率)
    private int rate;
    //磁力
    private int magnetism;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public long getAllotId() {
        return allotId;
    }

    public void setAllotId(long allotId) {
        this.allotId = allotId;
    }

    public int getFvId() {
        return fvId;
    }

    public void setFvId(int fvId) {
        this.fvId = fvId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getMagnetism() {
        return magnetism;
    }

    public void setMagnetism(int magnetism) {
        this.magnetism = magnetism;
    }
}
