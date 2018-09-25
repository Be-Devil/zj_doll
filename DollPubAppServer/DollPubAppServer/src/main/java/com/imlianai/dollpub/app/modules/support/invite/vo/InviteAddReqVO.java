package com.imlianai.dollpub.app.modules.support.invite.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class InviteAddReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 对方用户id
	 */
	@ParamCheck(errorMsg = "邀请码有误，请重新输入", format = FormatFinal.NUMBER)
	@ApiModelProperty("邀请码")
	private Long InviteCode;
	@ParamCheck(format = FormatFinal.NUMBER)
	@ApiModelProperty("邀请码类型 1-用户邀请码 2-官方邀请码")
	private int type;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getInviteCode() {
		return InviteCode;
	}
	public void setInviteCode(Long inviteCode) {
		InviteCode = inviteCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
