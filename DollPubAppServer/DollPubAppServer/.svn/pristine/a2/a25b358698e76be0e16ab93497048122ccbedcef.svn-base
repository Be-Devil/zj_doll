package com.imlianai.dollpub.app.modules.support.shopkeeper.dto;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeRecord;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 好友充值记录
 * @author wurui
 * @create 2018-05-02 11:14
 **/
@ApiModel("好友充值记录")
public class FriendTradeChargeDTO extends BaseModel {
    @ApiModelProperty(value = "UID")
    private long uid;
    @ApiModelProperty(value = "该好友所属层级(1:一级,2:二级)")
    private int level;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "充值日期")
    private String date;
    @ApiModelProperty(value = "店主收益金额")
    private Double money;
    @ApiModelProperty(value = "描述")
    private String remark;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FriendTradeChargeDTO() {
    }

    public static FriendTradeChargeDTO adapter(ShopkeeperIncomeRecord record, UserGeneral userGeneral){
        FriendTradeChargeDTO dto = new FriendTradeChargeDTO();
        if (record != null){
            if (userGeneral != null){
                dto.uid = userGeneral.getUid();
                dto.name = userGeneral.getName();
                dto.level = record.getLevel();
                dto.money = (1.0 * record.getDivideIncome()) / 100;
                dto.date = DateUtils.getFormatTimeStr(record.getCreateTime(),"yyyy-MM-dd HH:mm:ss");
                dto.remark = userGeneral.getName() + " 充值了" + (1.0 * record.getChargeMoney()) / 100 + "元";
            }
        }
        return dto;
    }


}
