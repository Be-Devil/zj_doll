package com.imlianai.zjdoll.app.modules.publics.msg.ios;

import org.json.JSONObject;

import com.imlianai.zjdoll.app.modules.publics.msg.common.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
