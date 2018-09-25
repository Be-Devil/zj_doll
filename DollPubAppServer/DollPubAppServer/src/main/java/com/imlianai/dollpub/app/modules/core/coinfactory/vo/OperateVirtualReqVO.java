package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-07-02 18:13
 **/
public class OperateVirtualReqVO extends BaseSignReqVO{
    @ApiModelProperty(value = "机器ID",notes = "请带上UID一起请求",required = true)
    private int busId;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

}
