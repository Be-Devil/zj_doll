package com.imlianai.dollpub.app.modules.support.machine.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机器基础设置请求对象
 * @author wurui
 * @create 2018-02-27 10:44
 **/
@ApiModel(value = "机器基础数据设置请求对象")
public class MachineBasicDataSetReqVO extends BaseSignReqVO {



    /**
     * 机器id
     */
    @ApiModelProperty(value = "机器id",required = true)
    private int busId;

    /**
     * 语言(取值范围（0-1）0为英文 ，1为中文)
     */
    @ApiModelProperty(value = "语言(0为英文 ，1为中文),取值范围（0-1）",required = true)
    private int language;

    /**
     * 广告音乐开关(取值范围（0-1）0音乐无，1音乐有)
     */
    @ApiModelProperty(value = "广告音乐开关(0音乐无，1音乐有),取值范围（0-1）" ,required = true)
    private int advertMusicOnOff;

    /**
     * 广告音乐间隔时间(取值范围（0-30）当广告音乐开关为1有效)
     */
    @ApiModelProperty(value = "广告音乐间隔时间(当广告音乐开关为1有效),取值范围（0-30）" ,required = true)
    private int advertMusicIntervalTime;

    /**
     * 币数(取值范围（1-10）币数设置)
     */
    @ApiModelProperty(value = "币数(币数设置),取值范围（1-10）" ,required = true)
    private int coin;

    /**
     * 局数(取值范围（1-10）局数设置)
     */
    @ApiModelProperty(value = "局数(局数设置),取值范围（1-10）" ,required = true)
    private int inning;

    /**
     * 币数保留(取值范围（0-1）0关机不保留，1关机保留)
     */
    @ApiModelProperty(value = "币数保留(0关机不保留，1关机保留),取值范围（0-1）" ,required = true)
    private int coinRetain;

    /**
     * 游戏时间(取值范围（5-60))
     */
    @ApiModelProperty(value = "游戏时间,取值范围（5-60)" ,required = true)
    private int playTime;

    /**
     * 游戏模式(取值范围（1-6）)
     */
    @ApiModelProperty(value = "游戏模式,取值范围（1-6）" ,required = true)
    private int gameType;

    /**
     * 出奖概率(取值范围（1-250）)
     */
    @ApiModelProperty(value = "出奖概率,取值范围（1-250）" ,required = true)
    private int probability;

    /**
     * 礼品出口位置(取值范围（0-1）0左后角，1左前角)
     */
    @ApiModelProperty(value = "礼品出口位置(0左后角，1左前角),取值范围（0-1）" ,required = true)
    private int exitPosition;

    /**
     * 空中抓物(取值范围（0-1）0为关，1为开)
     */
    @ApiModelProperty(value = "空中抓物(0为关，1为开),取值范围（0-1）" ,required = true)
    private int airGrip;

    /**
     * 背景音乐
     */
    @ApiModelProperty(value = "背景音乐",required = true)
    private int bgMusic;

    /**
     * 游戏音乐
     */
    @ApiModelProperty(value = "游戏音乐",required = true)
    private int gameMusic;

    /**
     * 光眼电平(取值范围（0-1）0为常开，1为常闭)
     */
    @ApiModelProperty(value = "光眼电平(0为常开，1为常闭),取值范围（0-1）" ,required = true)
    private int photoEye;

    /**
     * 摇晃清分(取值范围（0-1）)
     */
    @ApiModelProperty(value = "摇晃清分,取值范围（0-1）" ,required = true)
    private int shake;

    /**
     * 保留(取值范围（0-1）)
     */
    @ApiModelProperty(value = "保留,取值范围（0-1）" ,required = true)
    private int retain;


    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getAdvertMusicOnOff() {
        return advertMusicOnOff;
    }

    public void setAdvertMusicOnOff(int advertMusicOnOff) {
        this.advertMusicOnOff = advertMusicOnOff;
    }

    public int getAdvertMusicIntervalTime() {
        return advertMusicIntervalTime;
    }

    public void setAdvertMusicIntervalTime(int advertMusicIntervalTime) {
        this.advertMusicIntervalTime = advertMusicIntervalTime;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getInning() {
        return inning;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public int getCoinRetain() {
        return coinRetain;
    }

    public void setCoinRetain(int coinRetain) {
        this.coinRetain = coinRetain;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getExitPosition() {
        return exitPosition;
    }

    public void setExitPosition(int exitPosition) {
        this.exitPosition = exitPosition;
    }

    public int getAirGrip() {
        return airGrip;
    }

    public void setAirGrip(int airGrip) {
        this.airGrip = airGrip;
    }

    public int getBgMusic() {
        return bgMusic;
    }

    public void setBgMusic(int bgMusic) {
        this.bgMusic = bgMusic;
    }

    public int getGameMusic() {
        return gameMusic;
    }

    public void setGameMusic(int gameMusic) {
        this.gameMusic = gameMusic;
    }

    public int getPhotoEye() {
        return photoEye;
    }

    public void setPhotoEye(int photoEye) {
        this.photoEye = photoEye;
    }

    public int getShake() {
        return shake;
    }

    public void setShake(int shake) {
        this.shake = shake;
    }

    public int getRetain() {
        return retain;
    }

    public void setRetain(int retain) {
        this.retain = retain;
    }
}
