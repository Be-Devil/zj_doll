package com.imlianai.dollpub.app.modules.core.user.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class CustomerAuthUserInfo extends BaseModel {

	private String name;
	private String head;

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
