package com.imlianai.zjdoll.app.modules.support.version.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel(value = "审核状态返回")
public class VersionAuditRespVO extends BaseRespVO {

	@ApiModelProperty(value = "审核状态标识 true-审核中 false-非审核中")
	private boolean audit;

	public VersionAuditRespVO() {
	}

	public VersionAuditRespVO(boolean audit) {
		this.audit = audit;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

}
