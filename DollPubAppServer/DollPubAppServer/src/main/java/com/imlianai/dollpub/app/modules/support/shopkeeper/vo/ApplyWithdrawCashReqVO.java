package com.imlianai.dollpub.app.modules.support.shopkeeper.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-04-28 19:02
 **/
@ApiModel(value = "申请提现请求对象")
public class ApplyWithdrawCashReqVO extends BaseReqVO {

    @ApiModelProperty(value = "提现金额",required = true)
    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
