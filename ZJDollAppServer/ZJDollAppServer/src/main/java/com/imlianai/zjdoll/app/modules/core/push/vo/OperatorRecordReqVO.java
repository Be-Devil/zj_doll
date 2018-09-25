package com.imlianai.zjdoll.app.modules.core.push.vo;

import com.imlianai.rpc.support.common.BaseModel;

/**
 * @author wurui
 * @create 2018-06-14 10:21
 **/
public class OperatorRecordReqVO extends BaseModel {
    private long uid;
    private int busId;
    private long optId;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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
}
