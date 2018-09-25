package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "获取最近兑换的娃娃列表")
public class GetRecentExcListRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	List<ExchangeDollInfo> dollInfos;

	public List<ExchangeDollInfo> getDollInfos() {
		return dollInfos;
	}

	public void setDollInfos(List<ExchangeDollInfo> dollInfos) {
		this.dollInfos = dollInfos;
	}
}
