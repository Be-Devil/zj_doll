package com.imlianai.dollpub.app.modules.support.invite.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public class InviteInfoRespVO extends BaseRespVO{

	@ApiModelProperty("是否显示邀请码填写框")
	private boolean showInput;
	
	@ApiModelProperty("已接受的邀请码")
	private long invitedCode=0;
	
	@ApiModelProperty("邀请码内容描述，仅当showInput=false,invitedCode=0时显示该段文案")
	private String invitedHtml;

	@ApiModelProperty("官方邀请码文案")
	private String officalHtml;
	
	@ApiModelProperty("滚动栏")
	private List<String> srcollList;
	
	public boolean isShowInput() {
		return showInput;
	}

	public void setShowInput(boolean showInput) {
		this.showInput = showInput;
	}

	public long getInvitedCode() {
		return invitedCode;
	}

	public void setInvitedCode(long invitedCode) {
		this.invitedCode = invitedCode;
	}

	public String getInvitedHtml() {
		return invitedHtml;
	}

	public void setInvitedHtml(String invitedHtml) {
		this.invitedHtml = invitedHtml;
	}

	public String getOfficalHtml() {
		return officalHtml;
	}

	public void setOfficalHtml(String officalHtml) {
		this.officalHtml = officalHtml;
	}

	public List<String> getSrcollList() {
		return srcollList;
	}

	public void setSrcollList(List<String> srcollList) {
		this.srcollList = srcollList;
	}
	
	
}
