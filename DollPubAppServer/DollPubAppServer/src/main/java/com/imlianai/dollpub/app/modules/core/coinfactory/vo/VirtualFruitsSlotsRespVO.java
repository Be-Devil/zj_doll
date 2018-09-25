package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 水果拉霸机响应对象
 * @author wurui
 * @create 2018-08-14 12:00
 **/
public class VirtualFruitsSlotsRespVO extends BaseRespVO {

    @ApiModelProperty(value = "操作ID")
    private long optId;

    @ApiModelProperty(value = "游戏时间")
    private int playTime;

    @ApiModelProperty(value = "拉霸币数")
    private int slots;

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

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }
}
