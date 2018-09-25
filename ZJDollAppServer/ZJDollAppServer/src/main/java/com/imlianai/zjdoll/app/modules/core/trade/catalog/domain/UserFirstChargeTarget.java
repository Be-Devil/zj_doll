package com.imlianai.zjdoll.app.modules.core.trade.catalog.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

public class UserFirstChargeTarget extends BaseModel{

	private long uid;
	
	private int code;
	
	private int pushCount;
	
	private Date nextPushTime;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getNextPushTime() {
		return nextPushTime;
	}

	public void setNextPushTime(Date nextPushTime) {
		this.nextPushTime = nextPushTime;
	}

	public int getPushCount() {
		return pushCount;
	}

	public void setPushCount(int pushCount) {
		this.pushCount = pushCount;
	}
	
	
}
