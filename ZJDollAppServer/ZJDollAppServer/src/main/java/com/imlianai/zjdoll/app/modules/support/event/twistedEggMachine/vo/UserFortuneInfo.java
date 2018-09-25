package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.user.UserGeneral;

import io.swagger.annotations.ApiModelProperty;

public class UserFortuneInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户ID")
	private long uid;
	
	@ApiModelProperty(value = "用户名称")
	private String name;
	
	@ApiModelProperty(value = "用户头像")
	private String head;
	
	@ApiModelProperty(value = "用户排名")
	private int ranking;
	
	@ApiModelProperty(value = "手气值")
	private int value;
	
	public UserFortuneInfo() {
		
	}
	
	public UserFortuneInfo(UserGeneral userGeneral, int value, int ranking) {
		if(userGeneral != null) {
			this.uid = userGeneral.getUid();
			this.name = userGeneral.getName();
			this.head = userGeneral.getHead();
		}
		this.ranking = ranking;
		this.value = value;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
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

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
