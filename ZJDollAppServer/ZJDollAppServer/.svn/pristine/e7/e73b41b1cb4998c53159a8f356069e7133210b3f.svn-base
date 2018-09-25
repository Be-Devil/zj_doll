package com.imlianai.zjdoll.app.modules.support.playground.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.constants.BusOwnerConstants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("萌店列表信息返回对象")
public class BusOwnerListRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	List<BusOwnerItem> busOwnerItems;
	
	@ApiModelProperty(value = "规则h5标题")
	private String rulesTitle = BusOwnerConstants.RULES_H5_TITLE;
	
	@ApiModelProperty(value = "规则h5地址")
	private String rulesUrl = BusOwnerConstants.RULES_H5_URL;

	public List<BusOwnerItem> getBusOwnerItems() {
		return busOwnerItems;
	}

	public void setBusOwnerItems(List<BusOwnerItem> busOwnerItems) {
		this.busOwnerItems = busOwnerItems;
	}

	public String getRulesTitle() {
		return rulesTitle;
	}

	public void setRulesTitle(String rulesTitle) {
		this.rulesTitle = rulesTitle;
	}

	public String getRulesUrl() {
		return rulesUrl;
	}

	public void setRulesUrl(String rulesUrl) {
		this.rulesUrl = rulesUrl;
	}
}
