package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.domain.egg.EggMachine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("扭蛋机列表返回对象")
public class EggMacListRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("娃娃机信息列表")
	private List<EggMachine> eggMachineList;

	public List<EggMachine> getEggMachineList() {
		return eggMachineList;
	}

	public void setEggMachineList(List<EggMachine> eggMachineList) {
		this.eggMachineList = eggMachineList;
	}
}
