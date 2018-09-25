package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("扭蛋机列表请求对象")
public class EggMacListReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("分类类型，用于获取不同分类下的扭蛋机")
	private int type = 0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
