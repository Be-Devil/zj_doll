package com.imlianai.dollpub.app.modules.publics.msg.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@ApiModel("获取leancloud签名请求")
public class ImSignReqVO extends BaseReqVO {
	
	private static final long serialVersionUID = 1L;


	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("用户id")
	private int type;
	
	@ApiModelProperty("self_id")
	private String self_id;
	
	@ApiModelProperty("watch_ids")
	private List<String> watch_ids;

	@ApiModelProperty("conv_id")
	private String conv_id;
	
	@ApiModelProperty("action")
	private String action;
	
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

	public String getSelf_id() {
		return self_id;
	}

	public void setSelf_id(String self_id) {
		this.self_id = self_id;
	}

	public List<String> getWatch_ids() {
		return watch_ids;
	}

	public void setWatch_ids(List<String> watch_ids) {
		this.watch_ids = watch_ids;
	}

	public String getConv_id() {
		return conv_id;
	}

	public void setConv_id(String conv_id) {
		this.conv_id = conv_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
