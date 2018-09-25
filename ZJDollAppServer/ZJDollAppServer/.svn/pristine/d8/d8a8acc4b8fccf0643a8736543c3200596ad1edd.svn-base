package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.zjdoll.app.modules.support.redpacket.constants.RedpacketConstants;

public class BusRedpacket extends BaseModel {

	@ApiModelProperty("红包id")
	private long redpacketId;
	@ApiModelProperty("娃娃机id")
	private int busId;
	@ApiModelProperty("金额")
	private double amt;
	@ApiModelProperty("用户id")
	private long uid;
	@ApiModelProperty("红包标题")
	private String title="运气太好啦，天降红包！";
	@ApiModelProperty("红包下方描述")
	private String desc=RedpacketConstants.REG_AWARD_REDPACK_CONTENT;
	@ApiModelProperty("文案")
	private String content=RedpacketConstants.REG_AWARD_REDPACK_CONTENT;
	@ApiModelProperty("红包类型 0-普通天降红包 1-暴击红包")
	private int type=0;
	
	@ApiModelProperty("暴击红包分享代码")
	private String shareCode=RedpacketConstants.SUPER_REDPACK_SHARE_CODE;
	public BusRedpacket() {
	}

	public BusRedpacket(long uid, int busId, long redpacketId, double amt) {
		this.uid = uid;
		this.busId = busId;
		this.redpacketId = redpacketId;
		this.amt = amt;
	}

	public long getRedpacketId() {
		return redpacketId;
	}

	public void setRedpacketId(long redpacketId) {
		this.redpacketId = redpacketId;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void updateSuperRedpacket(){
		this.type=1;
		this.title=RedpacketConstants.BUS_SUPER_REDPACK_TITLE;
		this.desc=RedpacketConstants.BUS_SUPER_REDPACK_CONTENT;
	}
	public void updateSuperRedpacketShare(){
		this.type=1;
		this.title=RedpacketConstants.BUS_SUPER_REDPACK_TITLE_SHARE;
		this.desc=RedpacketConstants.BUS_SUPER_REDPACK_CONTENT;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
}
