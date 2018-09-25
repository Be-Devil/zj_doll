package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "开启红包返回对象")
public class RedpacketOpenRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	public RedpacketOpenRespVO() {
		
	}

	public RedpacketOpenRespVO(int result, boolean state, String msg) {
		super(result, state, msg);
	}
	
	@ApiModelProperty(value = "id")
	private Long id;
	
	@ApiModelProperty(value = "奖励类型(1:红包, 2:金币)")
	private int rewardType;
	
	@ApiModelProperty(value = "数量")
	private double amount;
	
	@ApiModelProperty(value = "是否为暴击红包(0:否，1:是)")
	private int isCrit;
	
	@ApiModelProperty(value = "红包说明")
	private String desc;
	
	@ApiModelProperty(value = "分享说明")
	private String shareDesc;
	
	@ApiModelProperty(value = "分享码")
	private String shareCode;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public int getIsCrit() {
		return isCrit;
	}

	public void setIsCrit(int isCrit) {
		this.isCrit = isCrit;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
}
