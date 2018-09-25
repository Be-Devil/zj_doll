package com.imlianai.dollpub.app.modules.core.api.vo;

import java.util.List;

import com.imlianai.dollpub.app.modules.core.api.dto.DollBusDTO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="娃娃机列表响应对象")
public class DollBusListRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="对应商户的娃娃机列表")
	private List<DollBusDTO> DollBusList;
	
//	@ApiModelProperty(value="当前页")
//	private int page;
//	
//	@ApiModelProperty(value="每页设备数量")
//	private int size;
//	
//	@ApiModelProperty(value="总数")
//	private int total_count;

	public List<DollBusDTO> getDollBusList() {
		return DollBusList;
	}

	public void setDollBusList(List<DollBusDTO> DollBusList) {
		this.DollBusList = DollBusList;
	}

	public DollBusListRespVO(List<DollBusDTO> dollBusList) {
		DollBusList = dollBusList;
	}

	public DollBusListRespVO() {
	}

//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getSize() {
//		return size;
//	}
//
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	public int getTotal_count() {
//		return total_count;
//	}
//
//	public void setTotal_count(int total_count) {
//		this.total_count = total_count;
//	}

	
	
}
