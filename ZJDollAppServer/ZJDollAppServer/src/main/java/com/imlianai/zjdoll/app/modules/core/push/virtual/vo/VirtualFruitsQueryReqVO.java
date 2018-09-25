package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;


import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * 虚拟水果机查奖请求对象
 * @author wurui
 * @create 2018-07-19 22:11
 **/
public class VirtualFruitsQueryReqVO extends BaseReqVO {

    private long optId;
    private int busId;

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
}
