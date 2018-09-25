package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@SuppressWarnings("serial")
@ApiModel("操作请求对象")
public class DollOperateReqVO extends BaseReqVO {

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("娃娃机id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer busId;

	@ApiModelProperty("上机操作id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private long optId;
	
	@ApiModelProperty("上机操作密钥")
	private long logKey;
	@ApiModelProperty("操作编码")
	private int opt;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}

	public long getLogKey() {
		return logKey;
	}

	public void setLogKey(long logKey) {
		this.logKey = logKey;
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

}
