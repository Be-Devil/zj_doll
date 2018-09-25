package com.imlianai.zjdoll.app.modules.support.userdoll.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取用户娃娃个数信息")
public class GetDollSizeInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Long uid;
	
	@ApiModelProperty(value = "用户名称")
	private String name;
	
	@ApiModelProperty(value = "用户头像")
	private String head;
	
	@ApiModelProperty(value = "娃娃总个数")
	private int size;

	public GetDollSizeInfo(){
		
	}
	
	public GetDollSizeInfo(Long uid, String name, String head, int size) {
		super();
		this.uid = uid;
		this.name = name;
		this.head = head;
		this.size = size;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
