package com.imlianai.dollpub.app.modules.core.api.dto;

import com.imlianai.dollpub.domain.optrecord.OptRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "操作记录传输对象")
public class OptRecordDTO {

	@ApiModelProperty(value = "操作id")
	private long optId;
	
	@ApiModelProperty(value = "设备id")
	private int busId;
	
	@ApiModelProperty(value = "用户id")
	private long uid;
	
	@ApiModelProperty(value = "抓取结果【1:成功,0:失败】")
	private int result;
	
	@ApiModelProperty(value = "备注")
	private String remark;
	
	public long getOptId() {
		return optId;
	}
	public void setOptId(long optId) {
		this.optId = optId;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public OptRecordDTO(long optId, int busId, long uid, int result, String remark) {
		super();
		this.optId = optId;
		this.busId = busId;
		this.uid = uid;
		this.result = result;
		this.remark = remark;
	}
	public OptRecordDTO() {
	}

	public OptRecordDTO(OptRecord optRecord) {
		if(optRecord != null) {
			this.optId = optRecord.getOptId();
			this.busId = optRecord.getBusId();
			this.uid = optRecord.getUid();
			this.result = optRecord.getResult();
			this.remark = optRecord.getRemark();
		}
	}
}
