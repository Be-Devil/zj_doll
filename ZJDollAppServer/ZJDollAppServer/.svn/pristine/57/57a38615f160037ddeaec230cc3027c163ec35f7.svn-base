package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("娃娃机推荐列表返回对象")
public class DollRecommendListRespVO extends BaseRespVO {

	@ApiModelProperty("推荐娃娃机信息列表")
	private List<DollBus> list;
	@ApiModelProperty("推荐热搜词")
	List<String> words;
	public List<DollBus> getList() {
		return list;
	}
	public void setList(List<DollBus> list) {
		this.list = list;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}

}
