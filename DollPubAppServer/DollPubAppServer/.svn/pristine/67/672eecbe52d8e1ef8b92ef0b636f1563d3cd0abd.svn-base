package com.imlianai.dollpub.app.modules.support.userdoll.vo;

import java.util.List;

import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户娃娃对应状态响应对象")
public class UserDollStateRespVO extends BaseRespVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "基础娃娃信息")
	private List<BaseDollInfo> baseDollInfos;
	
	@ApiModelProperty(value = "对应状态娃娃数量")
	private int count = 0;

	@ApiModelProperty(value = "全部娃娃数量")
	private int total;

	public List<BaseDollInfo> getBaseDollInfos() {
		return baseDollInfos;
	}

	public void setBaseDollInfos(List<BaseDollInfo> baseDollInfos) {
		this.baseDollInfos = baseDollInfos;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
