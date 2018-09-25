package com.imlianai.zjdoll.app.modules.support.version.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="版本更新请求")
public class VersionReqVO extends BaseReqVO{

	@ApiModelProperty(value="版本号")
	private Integer version;
	@ApiModelProperty(value="用户id",required=false)
	private Long uid;
	@ApiModelProperty(value="渠道号")
	private String channel;
	@ApiModelProperty(value="系统类型")
	private Integer osType;
	
	@ApiModelProperty(value="是否手动检查 1-手动检查 0-默认")
	private Integer isCheck=0;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getOsType() {
		return osType;
	}
	public void setOsType(int osType) {
		this.osType = osType;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	
	
}
