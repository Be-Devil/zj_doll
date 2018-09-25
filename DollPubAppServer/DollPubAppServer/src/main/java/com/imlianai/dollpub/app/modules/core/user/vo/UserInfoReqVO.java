package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@SuppressWarnings("serial")
@ApiModel("查看用户信息对象")
public class UserInfoReqVO extends BaseReqVO {

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("被查看用户id")
	private Long tid;

	@ApiModelProperty("页码，用于捉取列表分页")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer page;
	
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
}
