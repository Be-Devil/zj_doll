package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author wurui
 * @create 2018-01-23 11:50
 **/
@ApiModel(value = "娃娃机下爪请求对象")
public class DollBusDownClawReqVO extends BaseSignReqVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机器id", required = true)
    private int busId;

    @ApiModelProperty(value = "用户id", required = true)
    private Long uid;

    @ApiModelProperty(value = "操作id", required = true)
    private Long optId;

    @ApiModelProperty(value = "机器操作指令:【0】默认爪，【1】弱爪，【2】强爪", required = true)
    private int type;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getOptId() {
        return optId;
    }

    public void setOptId(Long optId) {
        this.optId = optId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
