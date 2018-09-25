package com.imlianai.zjdoll.app.modules.support.ranking.vo;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "榜单返回对象")
public class GetRankingRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "榜单列表")
	List<RankingItem> rankingItems;
	
	@ApiModelProperty(value = "自身排名信息")
	RankingItem selfRankingInfo;

	public List<RankingItem> getRankingItems() {
		return rankingItems;
	}

	public void setRankingItems(List<RankingItem> rankingItems) {
		this.rankingItems = rankingItems;
	}

	public RankingItem getSelfRankingInfo() {
		return selfRankingInfo;
	}

	public void setSelfRankingInfo(RankingItem selfRankingInfo) {
		this.selfRankingInfo = selfRankingInfo;
	}	
}
