package com.imlianai.dollpub.app.modules.core.coinfactory.dto;

import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.rpc.support.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-04-20 18:17
 **/
public class MachinePushCoinDTO {

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

    public static MachinePushCoinDTO adapter(MachinePushCoin machinePushCoin){
        MachinePushCoinDTO machinePushCoinDTO = new MachinePushCoinDTO();
        if (machinePushCoin != null){
            machinePushCoinDTO.busId = machinePushCoin.getBusId();
            machinePushCoinDTO.optId = machinePushCoin.getOptId();
            machinePushCoinDTO.uid = machinePushCoin.getUid();
            machinePushCoinDTO.intoCoin = machinePushCoin.getIntoCoin();
            machinePushCoinDTO.outCoin = machinePushCoin.getOutCoin();
            machinePushCoinDTO.startTime = DateUtils.dateToString(machinePushCoin.getStartTime(),DateUtils.DATETIME_PATTERN);
            if ( null != machinePushCoin.getEndTime()){
                machinePushCoinDTO.endTime = DateUtils.dateToString(machinePushCoin.getEndTime(),DateUtils.DATETIME_PATTERN);
            }else {
                machinePushCoinDTO.endTime = "";
            }
        }
        return machinePushCoinDTO;
    }

    public MachinePushCoinDTO() {
    }

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
}
