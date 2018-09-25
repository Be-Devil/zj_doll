package com.imlianai.dollpub.app.modules.core.dice.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * @author wurui
 * @create 2018-06-01 11:58
 **/
public class StopOperateReqVO extends BaseReqVO {

    private int busId;

    private long optId;

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
}
