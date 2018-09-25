package com.imlianai.zjdoll.app.modules.core.doll.vo;

import com.imlianai.zjdoll.domain.busowner.BusOwnerRes;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel("娃娃机进入返回对象")
public class BusEnterRespVO extends BaseRespVO {


	@ApiModelProperty("娃娃机信息")
	private DollBus info;
	@ApiModelProperty("游戏币余额")
	private int coin;
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
	@ApiModelProperty("是否已首冲")
	private boolean isPay=false;
	@ApiModelProperty("是否显示限时充值")
	private boolean isShowQPay=false;
	@ApiModelProperty("显示历史评论条数")
	private int msgNum=10;
	@ApiModelProperty("是否开通周月卡")
	private boolean isOpen;
	@ApiModelProperty("周月卡图片地址")
	private String imgWeekMonthCard = "http://lianai-image-sys.qiniudn.com/weekMonthCard/award.png";
	@ApiModelProperty("房间萌主信息")
	private BusOwnerRes busOwnerRes;
	@ApiModelProperty("欢迎语")
	private String welcomes;
	@ApiModelProperty("萌主h5页面地址")
	private String busOwnerUrl;
	@ApiModelProperty("萌主h5页面标题")
	private String busOwnerTitle;
	
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

	public boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(boolean isPay) {
		this.isPay = isPay;
	}

	public int getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}

	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getImgWeekMonthCard() {
		return imgWeekMonthCard;
	}

	public void setImgWeekMonthCard(String imgWeekMonthCard) {
		this.imgWeekMonthCard = imgWeekMonthCard;
	}

	public BusOwnerRes getBusOwnerRes() {
		return busOwnerRes;
	}

	public void setBusOwnerRes(BusOwnerRes busOwnerRes) {
		this.busOwnerRes = busOwnerRes;
	}

	public String getWelcomes() {
		return welcomes;
	}

	public void setWelcomes(String welcomes) {
		this.welcomes = welcomes;
	}

	public String getBusOwnerUrl() {
		return busOwnerUrl;
	}

	public void setBusOwnerUrl(String busOwnerUrl) {
		this.busOwnerUrl = busOwnerUrl;
	}

	public String getBusOwnerTitle() {
		return busOwnerTitle;
	}

	public void setBusOwnerTitle(String busOwnerTitle) {
		this.busOwnerTitle = busOwnerTitle;
	}

	public void setIsShowQPay(boolean isShowQPay) {
		this.isShowQPay = isShowQPay;
	}
	public boolean getIsShowQPay() {
		return this.isShowQPay;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
