package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "开启红包请求对象")
public class GetRedpacketReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "id")
	private Long id;
	
	@ApiModelProperty(value = "领取的奖励类型(0:邀请奖励红包，1:好友红包, 2:金币)", required=true)
	private int type;
	
	@ApiModelProperty(value = "开启的红包对应的用户UID", required=true)
	private Long tid;
	
	@ApiModelProperty(value = "数量(开启不传值，领取需传值)")
	private double amount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
