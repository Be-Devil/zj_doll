package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * @author wurui
 * @create 2018-07-02 15:07
 **/
public class EntryVirtualRoomReqVO extends BaseReqVO {
    private int busId;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }
}
