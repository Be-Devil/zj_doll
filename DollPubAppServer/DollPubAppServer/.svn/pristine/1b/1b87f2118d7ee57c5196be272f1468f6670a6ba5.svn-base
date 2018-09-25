package com.imlianai.dollpub.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.msg.MsgRoomJump;
import com.imlianai.dollpub.domain.user.UserSimple;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("娃娃机进入返回对象")
public class BusEnterRespVO extends BaseRespVO {


	@ApiModelProperty("娃娃机信息")
	private DollBus info;
	@ApiModelProperty("游戏币余额")
	private int coin;
	@ApiModelProperty("钻石")
	private int jewel;
	@ApiModelProperty("飘屏广告")
	private MsgRoomJump ad;
	@ApiModelProperty("当前玩家")
	private UserSimple player;
	@ApiModelProperty("公屏广告")
	private MsgRoomJump broad;
	@ApiModelProperty("壁纸")
	private String wallPaint;
	@ApiModelProperty("是否需要开启说话手机验证,true-开启 false-关闭")
	private boolean needTalkPhone=false;
	@ApiModelProperty("是否房间禁言,true-禁言 false-非禁言")
	private boolean slient=false;
	
	@ApiModelProperty("是否已充值,true-已充值 false-未充值")
	private boolean hasCharge=false;

	private int type;

	public DollBus getInfo() {
		return info;
	}

	public void setInfo(DollBus info) {
		this.info = info;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getJewel() {
		return jewel;
	}

	public void setJewel(int jewel) {
		this.jewel = jewel;
	}

	public UserSimple getPlayer() {
		return player;
	}

	public void setPlayer(UserSimple player) {
		this.player = player;
	}

	public MsgRoomJump getAd() {
		return ad;
	}

	public void setAd(MsgRoomJump ad) {
		this.ad = ad;
	}

	public MsgRoomJump getBroad() {
		return broad;
	}

	public void setBroad(MsgRoomJump broad) {
		this.broad = broad;
	}

	public String getWallPaint() {
		return wallPaint;
	}

	public void setWallPaint(String wallPaint) {
		this.wallPaint = wallPaint;
	}

	public boolean isNeedTalkPhone() {
		return needTalkPhone;
	}

	public void setNeedTalkPhone(boolean needTalkPhone) {
		this.needTalkPhone = needTalkPhone;
	}

	public boolean isSlient() {
		return slient;
	}

	public void setSlient(boolean slient) {
		this.slient = slient;
	}

	public boolean isHasCharge() {
		return hasCharge;
	}

	public void setHasCharge(boolean hasCharge) {
		this.hasCharge = hasCharge;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
