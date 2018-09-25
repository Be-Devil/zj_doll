package com.imlianai.dollpub.app.modules.publics.msg.ios;

import com.imlianai.dollpub.app.modules.publics.msg.common.IOSNotification;


public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
		
	}
}
