package com.imlianai.zjdoll.app.modules.core.egg.vo;


import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.domain.egg.EggMacClassifyInfo;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("获取扭蛋机分类返回对象")
public class EggMacClassifyRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<EggMacClassifyInfo> classifyList;

	public List<EggMacClassifyInfo> getClassifyList() {
		return classifyList;
	}

	public void setClassifyList(List<EggMacClassifyInfo> classifyList) {
		this.classifyList = classifyList;
	}
}
