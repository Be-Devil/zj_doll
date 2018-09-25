package com.imlianai.dollpub.app.modules.publics.signtest;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

@SuppressWarnings("serial")
public class SignTestReqVO extends BaseSignReqVO {

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("被查看用户id")
	private Long tid;

	@ApiModelProperty("页码，用于捉取列表分页")
	private Integer page;
	
	private String gameid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	
	
}
