package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("扭蛋机-用户上机返回对象")
public class PlayRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("操作ID")
	private Long optId;
	
	@ApiModelProperty("用户剩余时光劵")
	private int num;
	
	@ApiModelProperty("使用时光劵数量")
	private int useNum;
	
	@ApiModelProperty("奖品列表")
	private List<RewardInfo> rewardInfos;
	
	@ApiModelProperty("库存")
	private int inventory;
	
	@ApiModelProperty("中奖记录")
	private List<WinRecordItem> winRecords;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<RewardInfo> getRewardInfos() {
		return rewardInfos;
	}

	public void setRewardInfos(List<RewardInfo> rewardInfos) {
		this.rewardInfos = rewardInfos;
	}

	public Long getOptId() {
		return optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public List<WinRecordItem> getWinRecords() {
		return winRecords;
	}

	public void setWinRecords(List<WinRecordItem> winRecords) {
		this.winRecords = winRecords;
	}
}
