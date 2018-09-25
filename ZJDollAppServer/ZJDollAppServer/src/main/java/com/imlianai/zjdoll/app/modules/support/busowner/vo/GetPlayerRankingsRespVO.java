package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel("玩家排行榜单列表返回对象")
public class GetPlayerRankingsRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<PlayerRankingItem> playerRankingItems;

	public List<PlayerRankingItem> getPlayerRankingItems() {
		return playerRankingItems;
	}

	public void setPlayerRankingItems(List<PlayerRankingItem> playerRankingItems) {
		this.playerRankingItems = playerRankingItems;
	}
}
