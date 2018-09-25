package com.imlianai.dollpub.app.modules.support.machine.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机器爪力电压设置请求对象
 * @author wurui
 * @create 2018-02-27 16:18
 */
@ApiModel(value = "机器爪力电压设置请求对象")
public class MachineClawVoltageSetReqVO extends BaseSignReqVO {



    /**
     * 机器id
     */
    @ApiModelProperty(value = "机器id",required = true)
    private int busId;

    /**
     * 强爪电压 取值范围十进制（130-475），由高八位和低八位组合，010E十进制为270即27V
     */
    @ApiModelProperty(value = "强爪电压,取值范围十进制（130-475）",required = true)
    private int strongClawVoltage;

    /**
     * 弱爪电压 取值范围十进制（20-200），由高八位和低八位组合，0041十进制为65即6.5V
     */
    @ApiModelProperty(value = "弱爪电压 ,取值范围十进制（20-200）",required = true)
    private int weakClawVoltage;

    /**
     *  弱爪后电压 取值范围十进制（20-400），由高八位和低八位组合，0041十进制为65即6.5V
     */
    @ApiModelProperty(value = "弱爪后电压,取值范围十进制（20-400）",required = true)
    private int weakClawBackVoltage;

    /**
     * 中奖电压  取值范围十进制（45-480），由高八位和低八位组合，010E十进制为270即27V
     */
    @ApiModelProperty(value = "中奖电压,取值范围十进制（45-480）",required = true)
    private int winVoltage;




    /**
     * 强力维持时间(取值范围（1-30）08表示0.8秒)
     */
    @ApiModelProperty(value = "强力维持时间,取值范围（1-30）08表示0.8秒",required = true)
    private int strongForceTime;

    /**
     * 弱力维持时间(取值范围（1-30）08表示0.8秒)
     */
    @ApiModelProperty(value = "弱力维持时间,取值范围（1-30）08表示0.8秒",required = true)
    private int weakForceTime;

    /**
     * 强变弱方式(取值范围（0-1）0强爪时间后变弱抓 1碰到微动后变弱抓)
     */
    @ApiModelProperty(value = "强变弱方式(0强爪时间后变弱抓 1碰到微动后变弱抓),取值范围（0-1）",required = true)
    private int strongToWeakType;

    /**
     * 放线长度时间(取值范围（5-100）0C 表示1.2秒)
     */
    @ApiModelProperty(value = "放线长度时间,取值范围（5-100）,12表示1.2秒",required = true)
    private int payingOffLengthTime;

    /**
     * 收爪速度(取值范围（0-20）)
     */
    @ApiModelProperty(value = "收爪速度,取值范围（0-20）",required = true)
    private int closeClawSpeed;

    /**
     * 上升延时(取值范围（1-30）)
     */
    @ApiModelProperty(value = "上升延时,取值范围（1-30）",required = true)
    private int riseDelay;

    /**
     * 掉落延时比例(取值范围（0-99）)
     */
    @ApiModelProperty(value = "掉落延时比例，取值范围（0-99）",required = true)
    private int dropDelayRatio;

    /**
     * 掉落延时时间(取值范围（1-30）)
     */
    @ApiModelProperty(value = "掉落延时时间，取值范围（1-30）",required = true)
    private int dropDelayTime;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getStrongClawVoltage() {
        return strongClawVoltage;
    }

    public void setStrongClawVoltage(int strongClawVoltage) {
        this.strongClawVoltage = strongClawVoltage;
    }

    public int getWeakClawVoltage() {
        return weakClawVoltage;
    }

    public void setWeakClawVoltage(int weakClawVoltage) {
        this.weakClawVoltage = weakClawVoltage;
    }

    public int getWeakClawBackVoltage() {
        return weakClawBackVoltage;
    }

    public void setWeakClawBackVoltage(int weakClawBackVoltage) {
        this.weakClawBackVoltage = weakClawBackVoltage;
    }

    public int getWinVoltage() {
        return winVoltage;
    }

    public void setWinVoltage(int winVoltage) {
        this.winVoltage = winVoltage;
    }

    public int getStrongForceTime() {
        return strongForceTime;
    }

    public void setStrongForceTime(int strongForceTime) {
        this.strongForceTime = strongForceTime;
    }

    public int getWeakForceTime() {
        return weakForceTime;
    }

    public void setWeakForceTime(int weakForceTime) {
        this.weakForceTime = weakForceTime;
    }

    public int getStrongToWeakType() {
        return strongToWeakType;
    }

    public void setStrongToWeakType(int strongToWeakType) {
        this.strongToWeakType = strongToWeakType;
    }

    public int getPayingOffLengthTime() {
        return payingOffLengthTime;
    }

    public void setPayingOffLengthTime(int payingOffLengthTime) {
        this.payingOffLengthTime = payingOffLengthTime;
    }

    public int getCloseClawSpeed() {
        return closeClawSpeed;
    }

    public void setCloseClawSpeed(int closeClawSpeed) {
        this.closeClawSpeed = closeClawSpeed;
    }

    public int getRiseDelay() {
        return riseDelay;
    }

    public void setRiseDelay(int riseDelay) {
        this.riseDelay = riseDelay;
    }

    public int getDropDelayRatio() {
        return dropDelayRatio;
    }

    public void setDropDelayRatio(int dropDelayRatio) {
        this.dropDelayRatio = dropDelayRatio;
    }

    public int getDropDelayTime() {
        return dropDelayTime;
    }

    public void setDropDelayTime(int dropDelayTime) {
        this.dropDelayTime = dropDelayTime;
    }
}
