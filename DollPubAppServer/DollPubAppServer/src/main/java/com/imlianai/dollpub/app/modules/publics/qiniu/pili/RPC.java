package com.imlianai.dollpub.app.modules.publics.qiniu.pili;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;


final class RPC {

    private Mac mac;

    public RPC(){
    	this.mac =new Mac(Config.accessKey, Config.secretKey);
    }
    public RPC(Mac mac) {
    	if (mac!=null) {
    		this.mac = mac;
		}else{
			new RPC();
		}
    }
    public Mac getMac() {
        return mac;
    }
    
    public static String callWithJson(String urlStr, String json) throws Exception {
    	String contentType="application/json";
    	Map<String, String> headMap=new HashMap<String, String>();
    	headMap.put("Content-Type", contentType);
    	headMap.put("User-Agent", Config.APIUserAgent);
    	String macToken = Mac.signRequest(urlStr, "POST", json, contentType);
    	headMap.put("Authorization", "Qiniu " + macToken);
    	HttpEntity entity=HttpUtil.Post(urlStr, json,headMap);
    	System.out.println("callWithJson entity:"+JSON.toJSONString(entity));
    	return entity.getHtml();
    }

    public static String callWithGet(String urlStr) throws Exception {
    	String macToken = Mac.signRequest(urlStr, "GET", null, null);
    	Map<String, String> headMap=new HashMap<String, String>();
    	headMap.put("User-Agent", Config.APIUserAgent);
    	headMap.put("Authorization", "Qiniu " + macToken);
    	HttpEntity entity=HttpUtil.Get(urlStr,headMap);
    	return entity.getHtml();
    }

    public static String callWithDelete(String urlStr) throws Exception{
    	String macToken = Mac.signRequest(urlStr, "DELETE", null, null);
    	Map<String, String> headMap=new HashMap<String, String>();
    	headMap.put("User-Agent", Config.APIUserAgent);
    	headMap.put("Authorization", "Qiniu " + macToken);
    	HttpEntity entity=HttpUtil.Delete(urlStr,headMap);
    	return entity.getHtml();
    }
}
