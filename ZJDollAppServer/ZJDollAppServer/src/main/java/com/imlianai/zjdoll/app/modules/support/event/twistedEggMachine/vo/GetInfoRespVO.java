package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取扭蛋机信息返回对象")
public class GetInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户金币")
	private int coin;
	
	@ApiModelProperty(value = "任务扭蛋数量")
	private int teggNum;
	
	@ApiModelProperty(value = "抽中奖品记录")
	private List<UserAwardInfo> awardRecords;
	
	@ApiModelProperty(value = "手气值榜单列表")
	private List<UserFortuneInfo> rankingList;

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getTeggNum() {
		return teggNum;
	}

	public void setTeggNum(int teggNum) {
		this.teggNum = teggNum;
	}

	public List<UserAwardInfo> getAwardRecords() {
		return awardRecords;
	}

	public void setAwardRecords(List<UserAwardInfo> awardRecords) {
		this.awardRecords = awardRecords;
	}

	public List<UserFortuneInfo> getRankingList() {
		return rankingList;
	}

	public void setRankingList(List<UserFortuneInfo> rankingList) {
		this.rankingList = rankingList;
	}
}
