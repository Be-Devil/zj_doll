package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "人脸识别认证请求对象")
public class HandleFaceCerReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "姓名", required=true)
	private String name;
	
	@ApiModelProperty(value = "身份证", required=true)
	private String certificationId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertificationId() {
		return certificationId;
	}

	public void setCertificationId(String certificationId) {
		this.certificationId = certificationId;
	}
}
