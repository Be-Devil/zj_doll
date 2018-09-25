package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-01-24 15:58
 **/
@ApiModel(value = "第三方应用登陆请求对象")
public class UserSrcLoginReqVO extends BaseReqVO{

    @ApiModelProperty(value = "第三方用户id" ,required = true)
    private String srcId;
    @ApiModelProperty(value = "第三方授权码",required = true)
    private String srcCode;
    @ApiModelProperty(value = "商户id",required = true)
    private Integer customerId;

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int custonerId) {
        this.customerId = custonerId;
    }
}
