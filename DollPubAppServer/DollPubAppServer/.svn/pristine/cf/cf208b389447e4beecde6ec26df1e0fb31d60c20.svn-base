package com.imlianai.dollpub.app.modules.core.api.vo;



import com.imlianai.dollpub.app.modules.core.api.dto.OptRecordDTO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="娃娃机抓取结果响应对象")
public class DollBusResultRespVO extends BaseRespVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="结果")
	private OptRecordDTO optRecord;

	public OptRecordDTO getOptRecord() {
		return optRecord;
	}

	public void setOptRecord(OptRecordDTO optRecord) {
		this.optRecord = optRecord;
	}

	public DollBusResultRespVO(){}

	public DollBusResultRespVO(OptRecordDTO optRecordDTO){
		this.optRecord = optRecordDTO;
	}

}
