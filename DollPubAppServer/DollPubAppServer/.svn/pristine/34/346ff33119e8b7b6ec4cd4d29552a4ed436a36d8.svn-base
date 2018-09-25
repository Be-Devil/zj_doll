package com.imlianai.dollpub.app.modules.core.coinfactory.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-03-29 11:51
 **/
@ApiModel(value = "推币机操作结果查询请求对象")
public class PushCoinBusQueryReqVO extends BaseReqVO{

    @ApiModelProperty(value = "操作流水ID",required = true)
    private long optId;

    @ApiModelProperty(value = "商户ID",required = true)
    private int customerId;

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
