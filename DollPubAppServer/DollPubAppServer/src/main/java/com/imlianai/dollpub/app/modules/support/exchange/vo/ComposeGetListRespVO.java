package com.imlianai.dollpub.app.modules.support.exchange.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃合成列表返回对象")
public class ComposeGetListRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户普通娃娃碎片数量")
	private int commNum;
	
	@ApiModelProperty(value = "用户稀有娃娃碎片数量")
	private int rareNum;
	
	@ApiModelProperty(value = "可合成的娃娃列表")
	private List<ComposeDollInfo> composeDolls;

	public int getCommNum() {
		return commNum;
	}

	public void setCommNum(int commNum) {
		this.commNum = commNum;
	}

	public int getRareNum() {
		return rareNum;
	}

	public void setRareNum(int rareNum) {
		this.rareNum = rareNum;
	}

	public List<ComposeDollInfo> getComposeDolls() {
		return composeDolls;
	}

	public void setComposeDolls(List<ComposeDollInfo> composeDolls) {
		this.composeDolls = composeDolls;
	}
}
