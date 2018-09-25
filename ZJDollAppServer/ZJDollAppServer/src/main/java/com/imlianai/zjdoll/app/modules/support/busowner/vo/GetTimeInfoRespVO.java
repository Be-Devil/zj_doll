package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("当前轮时间信息返回对象")
public class GetTimeInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "开始日期的月份")
	private String sMonthStr;
	
	@ApiModelProperty(value = "开始日期的天数")
	private String sDayStr;
	
	@ApiModelProperty(value = "结束日期的月份")
	private String eMonthStr;
	
	@ApiModelProperty(value = "结束日期的天数")
	private String eDayStr;
	
	@ApiModelProperty(value = "剩余时间")
	private long remainingTime;

	public String getSMonthStr() {
		return sMonthStr;
	}

	public void setSMonthStr(String sMonthStr) {
		this.sMonthStr = sMonthStr;
	}

	public String getSDayStr() {
		return sDayStr;
	}

	public void setSDayStr(String sDayStr) {
		this.sDayStr = sDayStr;
	}

	public String getEMonthStr() {
		return eMonthStr;
	}

	public void setEMonthStr(String eMonthStr) {
		this.eMonthStr = eMonthStr;
	}

	public String getEDayStr() {
		return eDayStr;
	}

	public void setEDayStr(String eDayStr) {
		this.eDayStr = eDayStr;
	}

	public long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(long remainingTime) {
		this.remainingTime = remainingTime;
	}
}
