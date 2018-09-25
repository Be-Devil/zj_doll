package com.imlianai.dollpub.app.modules.support.pack.vo;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.pack.domain.GrabDollRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取用户抓取记录响应对象")
public class GetDollGrabRecordRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "娃娃抓取记录")
	private List<GrabDollRecord> grabDollRecords;
 
	public List<GrabDollRecord> getGrabDollRecords() {
		return grabDollRecords;
	}

	public void setGrabDollRecords(List<GrabDollRecord> grabDollRecords) {
		this.grabDollRecords = grabDollRecords;
	}
	
	

}
