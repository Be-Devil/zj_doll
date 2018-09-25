package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("手机验证码相关请求对象")
public class UserPhoneReqVO extends BaseReqVO {

	@ApiModelProperty("手机号码")
	private String number;

	@ApiModelProperty("验证码类型 1-注册 2-绑定手机 3-更换绑定 4-更改密码")
	private Integer checkCodeType;
	@ApiModelProperty("客户编号")
	private Integer customerId;
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCheckCodeType() {
		return checkCodeType;
	}

	public void setCheckCodeType(Integer checkCodeType) {
		this.checkCodeType = checkCodeType;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * 验证码类型
	 * 
	 * @author tensloveq
	 * 
	 */
	public enum CheckCodeType {

		REG(1, "手机注册"), BIND(2, "绑定手机"), CHANGE(3, "更换绑定"), RESET_PWD(4, "更换密码");

		public int type;
		public String des;

		private CheckCodeType(int type, String des) {
			this.type = type;
			this.des = des;
		}

	}
}
