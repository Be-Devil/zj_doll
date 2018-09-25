package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.dailytask.DailytaskTasks;

import io.swagger.annotations.ApiModelProperty;

public class TaskInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("可领取的活跃度值")
	private int activenessValue;

	@ApiModelProperty("任务说明")
	private String taskDesc;
	
	@ApiModelProperty("可领取的活跃度说明")
	private String activenessDesc;
	
	@ApiModelProperty("完成某个任务当前的数量")
	private int currNum;
	
	@ApiModelProperty("完成某个任务需要的数量")
	private int maxNum;
	
	@ApiModelProperty("任务状态(0:任务未完成,1:任务已完成，未领取活跃度,2:任务已完成，已领取活跃度)")
	private int status;
	
	@ApiModelProperty("跳转目标")
	private int jumpTarget;
	
	@ApiModelProperty("任务ID")
	private Long taskId;
	
	public TaskInfo(DailytaskTasks task) {
		this.taskDesc = task.getTaskDesc();
		this.activenessDesc = task.getActivenessDesc();
		this.maxNum = task.getMaxNum();
		this.jumpTarget = task.getJumpTarget();
		this.taskId = task.getTaskId();
		this.activenessValue = task.getAwardValue();
	}

	public int getActivenessValue() {
		return activenessValue;
	}

	public void setActivenessValue(int activenessValue) {
		this.activenessValue = activenessValue;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getActivenessDesc() {
		return activenessDesc;
	}

	public void setActivenessDesc(String activenessDesc) {
		this.activenessDesc = activenessDesc;
	}

	public int getCurrNum() {
		return currNum;
	}

	public void setCurrNum(int currNum) {
		this.currNum = currNum;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getJumpTarget() {
		return jumpTarget;
	}

	public void setJumpTarget(int jumpTarget) {
		this.jumpTarget = jumpTarget;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}
