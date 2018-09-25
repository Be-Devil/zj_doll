package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "每日任务信息返回对象")
public class DailytaskInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("今日活跃度")
	private int currActiveness;
	
	@ApiModelProperty("宝箱信息列表")
	private List<BoxInfo> boxInfoList;
	
	@ApiModelProperty("任务信息列表")
	List<TaskInfo> taskInfoList;

	@ApiModelProperty("分享邀请信息")
	private InviteInfo inviteInfo;
	
	public int getCurrActiveness() {
		return currActiveness;
	}

	public void setCurrActiveness(int currActiveness) {
		this.currActiveness = currActiveness;
	}

	public List<BoxInfo> getBoxInfoList() {
		return boxInfoList;
	}

	public void setBoxInfoList(List<BoxInfo> boxInfoList) {
		this.boxInfoList = boxInfoList;
	}

	public List<TaskInfo> getTaskInfoList() {
		return taskInfoList;
	}

	public void setTaskInfoList(List<TaskInfo> taskInfoList) {
		this.taskInfoList = taskInfoList;
	}

	public InviteInfo getInviteInfo() {
		return inviteInfo;
	}

	public void setInviteInfo(InviteInfo inviteInfo) {
		this.inviteInfo = inviteInfo;
	}
}
