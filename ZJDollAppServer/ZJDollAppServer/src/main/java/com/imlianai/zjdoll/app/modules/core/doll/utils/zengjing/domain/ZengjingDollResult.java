package com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain;

public class ZengjingDollResult {
	
	private int result;
	
	private boolean state;
	
	private String msg;
	
	private ZengjingOptRecord optRecord;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ZengjingOptRecord getOptRecord() {
		return optRecord;
	}

	public void setOptRecord(ZengjingOptRecord optRecord) {
		this.optRecord = optRecord;
	}
}
