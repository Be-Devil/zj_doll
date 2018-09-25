package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel("萌主基本信息返回对象")
public class GetBusOwnerInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<BusOwnerInfo> busOwnerInfos;

	public List<BusOwnerInfo> getBusOwnerInfos() {
		return busOwnerInfos;
	}

	public void setBusOwnerInfos(List<BusOwnerInfo> busOwnerInfos) {
		this.busOwnerInfos = busOwnerInfos;
	}
}
