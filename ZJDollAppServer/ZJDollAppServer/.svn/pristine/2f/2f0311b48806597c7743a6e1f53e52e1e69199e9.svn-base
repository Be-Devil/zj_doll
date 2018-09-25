package com.imlianai.zjdoll.app.modules.core.push.vo;

import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-03-29 15:28
 **/
@ApiModel(value = "查询操作结果返回对象")
public class PushCoinBusQueryRespVO extends BaseRespVO {

    @ApiModelProperty("操作ID")
    private long optId;

    @ApiModelProperty("机器ID")
    private int busId;

    @ApiModelProperty("用户ID")
    private long uid;

    @ApiModelProperty("游戏开始时间(上机开始计算)")
    private String startTime;

    @ApiModelProperty("游戏结束时间(最后一次投币为准 ，超过xx秒未投币则自动结束)")
    private String endTime;

    @ApiModelProperty("投币个数")
    private int intoCoin;

    @ApiModelProperty("出币个数")
    private int outCoin;

    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIntoCoin() {
        return intoCoin;
    }

    public void setIntoCoin(int intoCoin) {
        this.intoCoin = intoCoin;
    }

    public int getOutCoin() {
        return outCoin;
    }

    public void setOutCoin(int outCoin) {
        this.outCoin = outCoin;
    }

    public PushCoinBusQueryRespVO() {

    }

    public PushCoinBusQueryRespVO(MachinePushCoin machinePushCoin) {
        if (machinePushCoin != null){
            this.busId = machinePushCoin.getBusId();
            this.optId = machinePushCoin.getOptId();
            this.uid = machinePushCoin.getUid();
            this.intoCoin = machinePushCoin.getIntoCoin();
            this.outCoin = machinePushCoin.getOutCoin();
            this.startTime = DateUtils.dateToString(machinePushCoin.getStartTime(),DateUtils.DATETIME_PATTERN);
            if ( null != machinePushCoin.getEndTime()){
                this.endTime = DateUtils.dateToString(machinePushCoin.getEndTime(),DateUtils.DATETIME_PATTERN);
            }else {
                this.endTime = "";
            }
        }
    }



}
