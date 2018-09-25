package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("登录请求对象")
public class UserLoginReqVO extends BaseReqVO {

	@ApiModelProperty("手机号码/用户id")
	private String number;

	@ApiModelProperty("密码")
	private String pwd;

	@ApiModelProperty("验证码")
	private Integer checkCode;

	@ApiModelProperty("登录类型 0-手机 1-微信 2-QQ 3-微博")
	private Integer srcType=0;

	@ApiModelProperty("第三方登录Id")
	private String srcId;

	@ApiModelProperty("第三方登录-微信unionId")
	private String srcUnionId;
	
	@ApiModelProperty("第三方登录token")
	private String srcToken;
	
	@ApiModelProperty("账户登录多次密码错误的验证码")
	private String loginCode;
	
	@ApiModelProperty("客户编号")
	private Integer customerId;
	
	@ApiModelProperty("代理编号")
	private long agentId;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(Integer checkCode) {
		this.checkCode = checkCode;
	}

	public Integer getSrcType() {
		return srcType;
	}

	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getSrcToken() {
		return srcToken;
	}

	public void setSrcToken(String srcToken) {
		this.srcToken = srcToken;
	}

	public String getSrcUnionId() {
		return srcUnionId;
	}

	public void setSrcUnionId(String srcUnionId) {
		this.srcUnionId = srcUnionId;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public long getAgentId() {
		return agentId;
	}

	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	
}
