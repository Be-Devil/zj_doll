package com.imlianai.dollpub.app.modules.core.pinball.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * 操作弹珠机请求对象
 * @author wurui
 * @create 2018-07-13 22:41
 **/
public class OperatePinballReqVo extends BaseReqVO {
    private int busId;
    private int bet;
    private int force;
    private int power;

    private int item;
    private int low;
    private int high;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
}