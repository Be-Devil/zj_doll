package com.imlianai.zjdoll.app.modules.support.playground.vo;

import java.io.Serializable;
import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollBus;

import io.swagger.annotations.ApiModelProperty;

public class BusOwnerItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "娃娃机信息")
	private DollBus dollBus;
	
	@ApiModelProperty(value = "萌主榜单列表")
	List<BusOwnerRanking> busOwnerRankings;
	
	@ApiModelProperty(value = "收益排名描述")
	private String rankingDesc;

	public DollBus getDollBus() {
		return dollBus;
	}

	public void setDollBus(DollBus dollBus) {
		this.dollBus = dollBus;
	}

	public List<BusOwnerRanking> getBusOwnerRankings() {
		return busOwnerRankings;
	}

	public void setBusOwnerRankings(List<BusOwnerRanking> busOwnerRankings) {
		this.busOwnerRankings = busOwnerRankings;
	}

	public String getRankingDesc() {
		return rankingDesc;
	}

	public void setRankingDesc(String rankingDesc) {
		this.rankingDesc = rankingDesc;
	}
}
