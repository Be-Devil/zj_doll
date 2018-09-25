package com.imlianai.zjdoll.app.modules.support.shipping.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "申请发货请求对象")
public class ApplyReqVO extends BaseReqVO{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "收货地址ID", required = true)
	private Long addressId;
	
	@ApiModelProperty(value = "发货娃娃列表", required = true)
	private List<ApplyDollInfo> dollList;
	
	@ApiModelProperty(value = "备注")
	private String remark;
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public List<ApplyDollInfo> getDollList() {
		return dollList;
	}

	public void setDollList(List<ApplyDollInfo> dollList) {
		this.dollList = dollList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
