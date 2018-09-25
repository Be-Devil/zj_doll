package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.user.UserGeneral;

import io.swagger.annotations.ApiModelProperty;

public class PlayerRankingItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户UID")
	private Long uid;
	
	@ApiModelProperty(value = "用户名称")
	private String uname;
	
	@ApiModelProperty(value = "用户头像")
	private String uhead;
	
	@ApiModelProperty(value = "抓娃娃指数")
	private int catDollIndex;
	
	@ApiModelProperty(value = "分享值")
	private int shareValue;
	
	@ApiModelProperty(value = "机主指数")
	private int uindex;
	
	@ApiModelProperty(value = "排名(等于0表示未上榜)")
	private Integer ranking;
	
	public PlayerRankingItem() {
		
	}

	public PlayerRankingItem(UserGeneral userGeneral, int catDollIndex, int shareValue, int uindex, Integer ranking) {
		this.uid = userGeneral.getUid();
		this.uname = userGeneral.getName();
		this.uhead = userGeneral.getHead();
		this.catDollIndex = catDollIndex;
		this.shareValue = shareValue;
		this.uindex = uindex;
		this.ranking = ranking;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUhead() {
		return uhead;
	}

	public void setUhead(String uhead) {
		this.uhead = uhead;
	}

	public int getCatDollIndex() {
		return catDollIndex;
	}

	public void setCatDollIndex(int catDollIndex) {
		this.catDollIndex = catDollIndex;
	}

	public int getShareValue() {
		return shareValue;
	}

	public void setShareValue(int shareValue) {
		this.shareValue = shareValue;
	}

	public int getUindex() {
		return uindex;
	}

	public void setUindex(int uindex) {
		this.uindex = uindex;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
