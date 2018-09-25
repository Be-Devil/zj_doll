package com.imlianai.dollpub.app.modules.support.invite.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.invite.InviteH5RewardCatalog;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public class InviteListH5RespVO extends BaseRespVO{

	
	@ApiModelProperty("任务进度")
	private List<InviteH5RewardCatalog> list;
	
	@ApiModelProperty("已邀请人数")
	private int count;

	public List<InviteH5RewardCatalog> getList() {
		return list;
	}

	public void setList(List<InviteH5RewardCatalog> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
