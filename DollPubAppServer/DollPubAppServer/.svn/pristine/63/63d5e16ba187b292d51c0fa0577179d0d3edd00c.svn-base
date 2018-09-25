package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "操作记录信息")
public class OptRecordInfo extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("游戏编号")
	private long optId;

	@ApiModelProperty("娃娃名称")
	private String name;
	
	@ApiModelProperty("娃娃图片地址")
	private String path;
	
	@ApiModelProperty("时间")
	private long getTime;
	
	@ApiModelProperty("结果(0:失败，1:成功)")
	private int result;
	
	@ApiModelProperty("设备id")
	private long busId;
	
	public OptRecordInfo() {
	}
	
	public OptRecordInfo(long optId, String name, String path, long time, int result, long busId) {
		this.optId = optId;
		this.name = name;
		this.path = path;
		this.getTime = time;
		this.result = result;
		this.busId = busId;
	}
	
	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}
}
