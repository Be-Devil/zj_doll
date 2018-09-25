package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 虚拟推币机 上机推币操作
 * @author wurui
 * @create 2018-07-02 18:13
 **/
public class OperateVirtualPlayReqVO extends BaseSignReqVO{
    @ApiModelProperty(value = "机器ID",notes = "请带上UID一起请求",required = true)
    private int busId;

    @ApiModelProperty(value = "权重",notes = "权重越高分奖越多",required = true)
    private int weight;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
