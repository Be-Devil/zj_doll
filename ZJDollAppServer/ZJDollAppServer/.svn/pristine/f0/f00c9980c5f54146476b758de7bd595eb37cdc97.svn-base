package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@SuppressWarnings("serial")
@ApiModel("娃娃机信息列表请求对象")
public class DollListReqVO extends BaseReqVO {

	@ApiModelProperty( "用户id")
	private Long uid;

	@ApiModelProperty("页码，用于捉取列表分页")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer page;
	@ApiModelProperty("分类类型，用于获取不同分类下的娃娃机")
	private int type=0;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
