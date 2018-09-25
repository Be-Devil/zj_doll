package com.imlianai.zjdoll.app.modules.publics.umeng.common;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}
