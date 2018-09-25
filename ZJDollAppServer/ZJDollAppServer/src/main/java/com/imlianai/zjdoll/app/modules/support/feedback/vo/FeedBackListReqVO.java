package com.imlianai.zjdoll.app.modules.support.feedback.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="用户反馈列表请求")
public class FeedBackListReqVO extends BaseReqVO{
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private Long uid;
	/**
	 * 页码
	 */
	@ApiModelProperty(value="页码",notes="从1开始")
	private int page;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
}
