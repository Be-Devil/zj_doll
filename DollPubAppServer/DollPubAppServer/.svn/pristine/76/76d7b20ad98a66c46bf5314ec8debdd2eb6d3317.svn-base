package com.imlianai.dollpub.app.modules.publics.qiniu.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class QiniuGetTokenReqVO extends BaseReqVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long uid;

	/**
	 * 图片类型
	 */
	@ApiModelProperty("图片类型 0-用户头像 1-用户反馈")
	private Integer type;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "QiniuGetTokenReqVO [uid=" + uid + ", type=" + type + "]";
	}

}
