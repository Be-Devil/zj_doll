package com.imlianai.zjdoll.app.modules.core.push.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 上机响应对象
 * @author wurui
 * @create 2018-04-02 16:59
 **/
@ApiModel(value = "上机操作响应对象")
public class ApplyRespVO extends BaseRespVO {

    @ApiModelProperty(value = "操作ID",notes = "上机成功才有返回")
    private long optId;

    @ApiModelProperty(value = "游戏时间",notes = "上机成功才有返回")
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
