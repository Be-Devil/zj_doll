package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "我的红包返回对象")
public class MyRedpacketRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户游戏币")
	private int coin;
	
	@ApiModelProperty(value = "我的红包信息")
	private MyRedpacketInfo myRedpacketInfo;
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public MyRedpacketInfo getMyRedpacketInfo() {
		return myRedpacketInfo;
	}

	public void setMyRedpacketInfo(MyRedpacketInfo myRedpacketInfo) {
		this.myRedpacketInfo = myRedpacketInfo;
	}
}
