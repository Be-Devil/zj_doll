package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃机上机操作请求对象")
public class DollBusApplyMachineReqVO extends BaseSignReqVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id", required = true)
	private Long uid;

	@ApiModelProperty(value = "机器id", required = true)
	private int busId;

	@ApiModelProperty(value = "备注")
	private String remark ;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
