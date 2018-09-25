package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-03-29 15:40
 **/
@ApiModel(value = "虚拟推币机操作响应对象")
public class OperateVirtualRespVO extends BaseRespVO {
    @ApiModelProperty(value = "操作ID")
    private long optId;

    @ApiModelProperty(value = "游戏时间")
    private int playTime;

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
}
