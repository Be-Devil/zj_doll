package com.imlianai.dollpub.app.modules.support.invite.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class InviteInfoReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 页面
	 */
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
