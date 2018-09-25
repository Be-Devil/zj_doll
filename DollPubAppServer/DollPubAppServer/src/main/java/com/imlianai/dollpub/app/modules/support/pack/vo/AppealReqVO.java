package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "申诉请求对象")
public class AppealReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "操作ID", required = true)
	private long optId;
	
	@ApiModelProperty(value = "理由", required = true)
	private String reason;

	@ApiModelProperty(value="商户id",required = true)
	private int customerId;


	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
