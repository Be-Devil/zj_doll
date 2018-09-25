package com.imlianai.zjdoll.app.modules.publics.msg.android;

import com.imlianai.zjdoll.app.modules.publics.msg.common.AndroidNotification;


public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
