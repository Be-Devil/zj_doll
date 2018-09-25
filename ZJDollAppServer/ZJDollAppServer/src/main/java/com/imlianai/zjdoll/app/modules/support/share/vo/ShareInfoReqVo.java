package com.imlianai.zjdoll.app.modules.support.share.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class ShareInfoReqVo extends BaseReqVO {

	private Long uid;

	@ApiModelProperty("分享代码")
	private String shareCode;
	
	private int id;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
