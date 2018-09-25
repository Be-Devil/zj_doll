package com.imlianai.zjdoll.app.modules.support.feedback.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="反馈详情请求对象")
public class FeedBackDetailReqVO extends BaseReqVO{
	
	@ApiModelProperty(value = "反馈id")
	private long id;
	@ApiModelProperty(value = "用户id")
	private Long uid;
	@ApiModelProperty(value="页码",notes="从1开始")
	private int page;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
