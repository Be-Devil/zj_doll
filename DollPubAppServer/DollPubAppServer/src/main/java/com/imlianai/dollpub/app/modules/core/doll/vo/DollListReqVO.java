package com.imlianai.dollpub.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@SuppressWarnings("serial")
@ApiModel("娃娃机信息列表请求对象")
public class DollListReqVO extends BaseReqVO {

	@ApiModelProperty( "用户id")
	private Long uid;

	@ApiModelProperty("页码，用于捉取列表分页")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer page;
	@ApiModelProperty( "客户id")
	private Integer customerId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
}
