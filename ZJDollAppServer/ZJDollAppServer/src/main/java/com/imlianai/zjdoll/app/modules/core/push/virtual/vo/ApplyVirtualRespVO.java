package com.imlianai.zjdoll.app.modules.core.push.virtual.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 虚拟推币机上机响应对象
 * @author wurui
 * @create 2018-04-02 16:59
 **/
@ApiModel(value = "上机操作响应对象")
public class ApplyVirtualRespVO extends BaseRespVO {

    @ApiModelProperty(value = "操作ID",notes = "上机成功才有返回")
    private long optId;

    @ApiModelProperty(value = "游戏时间",notes = "上机成功才有返回")
    private int playTime;

    @ApiModelProperty(value = "挡板倾斜度",notes = "上机成功才有返回")
    private int lean;

    @ApiModelProperty(value = "磁力",notes = "上机成功才有返回")
    private int magnetism;

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

    public int getLean() {
        return lean;
    }

    public void setLean(int lean) {
        this.lean = lean;
    }

    public int getMagnetism() {
        return magnetism;
    }

    public void setMagnetism(int magnetism) {
        this.magnetism = magnetism;
    }
}
