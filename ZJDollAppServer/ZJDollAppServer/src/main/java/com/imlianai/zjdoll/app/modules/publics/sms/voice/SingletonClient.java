package com.imlianai.zjdoll.app.modules.publics.sms.voice;

import com.imlianai.zjdoll.app.modules.publics.sms.SMSSender2;

public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		if(client==null){
			try {
				client=new Client(SMSSender2.softwareSerialNo, SMSSender2.key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
