package com.imlianai.zjdoll.app.modules.core.user.vo;

import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel("用户基础信息返回对象")
public class UserBaseInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private long uid;
	
	private String name;
	
	private String head;
	
	public UserBaseInfoRespVO() {
		
	}

	public UserBaseInfoRespVO(UserCommon userCommon) {
		super();
		this.uid = userCommon.getUid();
		this.name = userCommon.getName();
		this.head = userCommon.getHead();
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}
