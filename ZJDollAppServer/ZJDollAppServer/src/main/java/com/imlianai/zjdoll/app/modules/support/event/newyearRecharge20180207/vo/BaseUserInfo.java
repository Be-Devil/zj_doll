package com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.vo;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModelProperty;

public class BaseUserInfo extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	@ApiModelProperty(value = "昵称")
	private String name;
	
	@ApiModelProperty(value = "头像")
	private String head;
	
	@ApiModelProperty(value = "福气值")
	private double value;
	
	@ApiModelProperty(value = "排名")
	private int ranking;
	
	public BaseUserInfo() {
		
	}
	
	public BaseUserInfo(UserGeneral userGeneral, double value) {
		this.uid = userGeneral.getUid();
		this.name = userGeneral.getName();
		this.head = userGeneral.getHead();
		this.value = value;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}
