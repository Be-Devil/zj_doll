package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 水果拉霸机响应对象
 * @author wurui
 * @create 2018-08-14 12:00
 **/
public class VirtualFruitsSlotsReqVO extends BaseReqVO {

    @ApiModelProperty(value = "机器ID",notes = "请带上UID一起请求",required = true)
    private int busId;

    @ApiModelProperty(value = "操作ID",notes = "请带上UID一起请求",required = true)
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
