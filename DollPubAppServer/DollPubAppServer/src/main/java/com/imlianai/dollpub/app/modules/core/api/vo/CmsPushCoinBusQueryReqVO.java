package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-03-29 11:51
 **/
@ApiModel(value = "推币机操作结果查询请求对象")
public class CmsPushCoinBusQueryReqVO extends BaseSignReqVO {

    @ApiModelProperty(value = "操作流水ID",required = true)
    private long optId;

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }


}
