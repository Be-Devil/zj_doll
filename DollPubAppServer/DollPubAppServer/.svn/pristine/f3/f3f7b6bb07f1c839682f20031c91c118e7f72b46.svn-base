package com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo;

import com.imlianai.rpc.support.utils.StringUtil;

public class QiyiguoDollResult {

	private String user_id;

	private String device_id;

	private String operate_result;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getOperate_result() {
		return operate_result;
	}

	public void setOperate_result(String operate_result) {
		this.operate_result = operate_result;
	}

	public boolean isSuccessCatch(){
		if (!StringUtil.isNullOrEmpty(this.operate_result)&&this.operate_result.trim().equals("1")) {
			return true;
		}
		return false;
	}
}
