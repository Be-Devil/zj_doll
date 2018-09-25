package com.imlianai.dollpub.app.modules.core.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

@ApiModel(value = "娃娃抓取结果请求")
public class DollOptResultReqVO extends BaseSignReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "操作id", required = true)
	private Long optId;

	public Long getOptId() {
		return optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
	}

}
