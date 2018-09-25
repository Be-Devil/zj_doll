package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("娃娃机观众列表返回对象")
public class BusWatchersRespVO extends BaseRespVO {

	@ApiModelProperty("观众列表")
	private List<UserCommon> watchers;
	@ApiModelProperty("当前观看人数")
	private int watcherNum=10;

	public List<UserCommon> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<UserCommon> watchers) {
		this.watchers = watchers;
	}

	public int getWatcherNum() {
		return watcherNum;
	}

	public void setWatcherNum(int watcherNum) {
		this.watcherNum = watcherNum;
	}
	
}
