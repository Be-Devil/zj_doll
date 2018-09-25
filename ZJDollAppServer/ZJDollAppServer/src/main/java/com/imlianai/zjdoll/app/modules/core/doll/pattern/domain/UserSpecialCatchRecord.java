package com.imlianai.zjdoll.app.modules.core.doll.pattern.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class UserSpecialCatchRecord extends BaseModel{

	
	private long uid;
	
	private int strongCount;
	
	private int strongSuccess;
	
	private long lastOptId;
	
	private int successExpect;
	
	private int totalCatch;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getStrongCount() {
		return strongCount;
	}

	public void setStrongCount(int strongCount) {
		this.strongCount = strongCount;
	}

	public int getStrongSuccess() {
		return strongSuccess;
	}

	public void setStrongSuccess(int strongSuccess) {
		this.strongSuccess = strongSuccess;
	}

	public long getLastOptId() {
		return lastOptId;
	}

	public void setLastOptId(long lastOptId) {
		this.lastOptId = lastOptId;
	}

	public int getSuccessExpect() {
		return successExpect;
	}

	public void setSuccessExpect(int successExpect) {
		this.successExpect = successExpect;
	}

	public int getTotalCatch() {
		return totalCatch;
	}

	public void setTotalCatch(int totalCatch) {
		this.totalCatch = totalCatch;
	}
	
}
