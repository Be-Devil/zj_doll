package com.imlianai.dollpub.app.modules.support.shopkeeper.dto;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperWithdrawRecord;
import com.imlianai.rpc.support.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-05-15 15:23
 **/
@ApiModel("提现记录")
public class WithdrawRecordDTO {

    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "状态")
    private String statusDesc;
    @ApiModelProperty(value = "描述")
    private String createTime;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static WithdrawRecordDTO adapter(ShopkeeperWithdrawRecord record) {
        WithdrawRecordDTO withdrawRecordDTO = new WithdrawRecordDTO();
        if (record != null) {
            withdrawRecordDTO.setCreateTime(DateUtils.dateToString(record.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            withdrawRecordDTO.setRemark("提现了 " + 1.0 * (record.getaMoney() / 100) + " 元");
            withdrawRecordDTO.setStatusDesc(ShopkeeperWithdrawRecord.WithdrawStatusEnum.getDescByStatus(record.getStatus()));
        }
        return withdrawRecordDTO;
    }

}
