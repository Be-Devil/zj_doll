package com.imlianai.dollpub.app.modules.core.user.vo;

import java.util.List;

import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查看用户娃娃返回对象")
public class GetDollsRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户捉取到娃娃个数信息")
	private RankingItem userInfo;
	
	@ApiModelProperty(value = "用户娃娃列表")
	private List<BaseDollInfo> dollList;

	public RankingItem getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(RankingItem userInfo) {
		this.userInfo = userInfo;
	}

	public List<BaseDollInfo> getDollList() {
		return dollList;
	}

	public void setDollList(List<BaseDollInfo> dollList) {
		this.dollList = dollList;
	}
}
