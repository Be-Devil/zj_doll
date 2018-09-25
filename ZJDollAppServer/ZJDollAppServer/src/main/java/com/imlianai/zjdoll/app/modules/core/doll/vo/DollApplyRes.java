package com.imlianai.zjdoll.app.modules.core.doll.vo;

import com.imlianai.zjdoll.domain.doll.DollOptRecord;

public class DollApplyRes {

	private DollOptRecord record;

	private boolean success;

	private String msg;

	public DollApplyRes() {
	}

	public DollApplyRes(boolean success, String msg) {
		this.success=success;
		this.msg=msg;
	}

	public DollOptRecord getRecord() {
		return record;
	}

	public void setRecord(DollOptRecord record) {
		this.record = record;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
