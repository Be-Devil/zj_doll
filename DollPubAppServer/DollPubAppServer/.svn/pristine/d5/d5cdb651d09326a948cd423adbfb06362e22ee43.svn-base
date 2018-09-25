package com.imlianai.dollpub.app.modules.publics.msg.leancloud;

import java.util.Map;

/**
 * 苹果推送对象
 * 
 * @author tensloveq
 * 
 */
public class AppleNotification {

	private long uid;
	/**
	 * 苹果授权token,与uid 2选一
	 */
	private String appleToken;
	/**
	 * 提示内容
	 */
	private String alertMsg;
	/**
	 * 额外字段
	 */
	private Map<String, Object> extraMap;

	public AppleNotification(long uid, String alertMsg) {
		this.uid = uid;
		this.alertMsg = alertMsg;
	}

	public AppleNotification(String appleToken, String alertMsg) {
		this.appleToken = appleToken;
		this.alertMsg = alertMsg;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getAppleToken() {
		return appleToken;
	}

	public void setAppleToken(String appleToken) {
		this.appleToken = appleToken;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public Map<String, Object> getExtraMap() {
		return extraMap;
	}

	public void setExtraMap(Map<String, Object> extraMap) {
		this.extraMap = extraMap;
	}

	@Override
	public String toString() {
		return "AppleNotification [uid=" + uid + ", appleToken=" + appleToken
				+ ", alertMsg=" + alertMsg + ", extraMap=" + extraMap + "]";
	}

}
