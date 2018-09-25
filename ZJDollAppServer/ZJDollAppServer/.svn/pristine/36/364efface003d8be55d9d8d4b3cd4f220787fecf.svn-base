package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModelProperty;

public class InviteRedpacketRes extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "用户UID")
	private Long uid;
	
	@ApiModelProperty(value = "用户头像")
	private String head;
	
	@ApiModelProperty(value = "用户姓名")
	private String name;
	
	@ApiModelProperty(value = "说明")
	private String remark;
	
	@ApiModelProperty(value = "倒计时时间(s)")
	private long countDownTime;
	
	@ApiModelProperty(value = "0:需要扣取积分,1:免费开启")
	private int freeFlag;
	
	public InviteRedpacketRes() {
		
	}

	public InviteRedpacketRes(Long id, Long uid, String head, String name, String remark, long countDownTime, int freeFlag) {
		this.id = id;
		this.uid = uid;
		this.head = head;
		this.name = name;
		this.remark = remark;
		this.countDownTime = countDownTime;
		this.freeFlag = freeFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCountDownTime() {
		return countDownTime;
	}

	public void setCountDownTime(long countDownTime) {
		this.countDownTime = countDownTime;
	}

	public int getFreeFlag() {
		return freeFlag;
	}

	public void setFreeFlag(int freeFlag) {
		this.freeFlag = freeFlag;
	}
}
