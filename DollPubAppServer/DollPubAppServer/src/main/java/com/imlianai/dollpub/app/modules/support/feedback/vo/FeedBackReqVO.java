package com.imlianai.dollpub.app.modules.support.feedback.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="添加反馈请求对象")
public class FeedBackReqVO extends BaseReqVO{
	
	@ApiModelProperty(value = "反馈类型 1咨询；2建议；3故障；4投诉；5充值")
	private int type;
	@ApiModelProperty(value = "用户id")
	private Long uid;
	@ApiModelProperty(value = "内容")
	private String content;
	@ApiModelProperty(value = "反馈图片")
	private String filename;
	@ApiModelProperty(value = "联系方式")
	private String phone;
	
	@ApiModelProperty( "客户id")
	private int customerId;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
}
