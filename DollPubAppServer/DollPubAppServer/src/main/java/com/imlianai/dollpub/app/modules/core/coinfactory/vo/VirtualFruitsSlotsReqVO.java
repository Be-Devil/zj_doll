package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

/**
 * 水果拉霸机请求对象
 * @author wurui
 * @create 2018-08-14 12:00
 **/
public class VirtualFruitsSlotsReqVO extends BaseSignReqVO {

    //操作id
    private long optId;
    //机器id
    private int busId;
    //权重
    private int weight;
    //拉霸币数
    private int coin;

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
