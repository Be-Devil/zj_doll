package com.imlianai.dollpub.app.modules.support.userdoll.vo;

import java.util.List;

import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModelProperty;

public class UserDollRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户娃娃列表")
	private List<BaseDollInfo> dollList;
	
	@ApiModelProperty(value = "用户信息")
	private RankingItem userInfo;
	
	@ApiModelProperty(value = "用户捉取到的娃娃总数")
	private int allNum;
	
	@ApiModelProperty(value = "用户已兑换的娃娃总数")
	private int exNum;
	
	@ApiModelProperty(value = "用户娃娃寄存中总个数")
	private int checkNum;

	@ApiModelProperty(value = "用户娃娃已发货总个数")
	private int shipkNum;
	
	public List<BaseDollInfo> getDollList() {
		return dollList;
	}

	public void setDollList(List<BaseDollInfo> dollList) {
		this.dollList = dollList;
	}

	public RankingItem getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(RankingItem userInfo) {
		this.userInfo = userInfo;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getExNum() {
		return exNum;
	}

	public void setExNum(int exNum) {
		this.exNum = exNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getShipkNum() {
		return shipkNum;
	}

	public void setShipkNum(int shipkNum) {
		this.shipkNum = shipkNum;
	}
	
	
	
	
}
