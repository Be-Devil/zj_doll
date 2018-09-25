package com.imlianai.dollpub.app.modules.core.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@ApiModel(value = "娃娃录播生成请求")
public class DollVideoReqVO extends BaseSignReqVO {

	private static final long serialVersionUID = 1L;

	@ParamCheck
	@ApiModelProperty(value = "流地址", required = true)
	private String streamUrl;
	@ParamCheck
	@ApiModelProperty(value = "开始时间秒戳", required = true)
	private long start;
	@ParamCheck
	@ApiModelProperty(value = "结束时间秒戳", required = true)
	private long end;
	public String getStreamUrl() {
		return streamUrl;
	}
	public void setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

}
