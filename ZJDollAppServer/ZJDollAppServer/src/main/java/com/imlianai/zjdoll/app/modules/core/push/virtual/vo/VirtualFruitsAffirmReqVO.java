package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * 虚拟水果机确认中奖请求对象
 * @author wurui
 * @create 2018-07-23 15:11
 **/
public class VirtualFruitsAffirmReqVO extends BaseReqVO {

    private long allotId;

    private int busId;

    private long optId;

    private int result;

    public long getAllotId() {
        return allotId;
    }

    public void setAllotId(long allotId) {
        this.allotId = allotId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
