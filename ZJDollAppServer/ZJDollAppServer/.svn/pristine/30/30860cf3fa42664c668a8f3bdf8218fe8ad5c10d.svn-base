package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "宝箱信息对象")
public class BoxInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("宝箱ID")
	private int boxId;
	
	@ApiModelProperty("宝箱可开启所需的活跃度")
	private int activeness;
	
	@ApiModelProperty("开启宝箱的活跃度文案")
	private String activenessDesc;
	
	@ApiModelProperty("宝箱状态(0:不可开启,1:可开启,2:已开启)")
	private int status;
	
	@ApiModelProperty("宝箱开启奖励列表")
	private List<AwardInfo> awardList;
	
	public BoxInfo() {
		
	}
	
	public BoxInfo(int boxId, int activeness, String activenessDesc) {
		this.boxId = boxId;
		this.activeness = activeness;
		this.activenessDesc = activenessDesc;
	}
	
	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public int getActiveness() {
		return activeness;
	}

	public void setActiveness(int activeness) {
		this.activeness = activeness;
	}

	public String getActivenessDesc() {
		return activenessDesc;
	}

	public void setActivenessDesc(String activenessDesc) {
		this.activenessDesc = activenessDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<AwardInfo> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<AwardInfo> awardList) {
		this.awardList = awardList;
	}
}
