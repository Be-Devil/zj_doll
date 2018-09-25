package com.imlianai.zjdoll.app.modules.publics.umeng.common;

import java.util.HashMap;
import java.util.Map;

public class UMengConstants {

	/**
	 * 友盟推送appkey android
	 */
	//public static String Android_APPKey = "5a0aa518f29d982f6f000044";

	public static String AndroidDefault="doll";
	/**
	 * 友盟推送message secret android
	 */
	public static String Android_Umeng_Message_Secret = "381ecc5b414a9c9be0edf1a045b47095";

	/**
	 * 友盟推送master secret android
	 */
	public static String Android_App_Master_Secret = "l4rjec6x7vbghsfafxwnhmz37ke3sogg";

	/**
	 * 友盟推送appkey
	 */
	public static String Ios_APPKey = "5aab2c3af43e4814130000f8";

	/**
	 * 友盟推送master secret
	 */
	public static String Ios_App_Master_Secret = "mcq4lip0s8d6ltymstlhf362ujhmilin";
	/**
	 * IOS的Channel跟AppKey对应关系
	 */
	public static Map<String, String> IosChannel_AppKey_Secret_Map = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("appstore2", "5aab2d70f29d984f02000153,hc7sdsrjcdyt7ojtc3litslsqxfymeaf");
			put("appstore", "5aab2c3af43e4814130000f8,mcq4lip0s8d6ltymstlhf362ujhmilin");
			put("appstore3", "5ab9e417b27b0a061c0000bf,pn7emj4gz8donfkx9vvdlexejesyviit");
			put("appstore4", "5abb51aff43e480f25000028,ctpwrw67jvi5jdxda6wd96soiqgf8lgz");
		}
	};
	
	public static Map<String, String> AndroidChannel_AppKey_Secret_Map = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//Appkey,Umeng Message Secret,App Master Secret
		{
			put("doll", "5a0aa518f29d982f6f000044,381ecc5b414a9c9be0edf1a045b47095,l4rjec6x7vbghsfafxwnhmz37ke3sogg");
			put("meibao", "5ab4aef5f29d981daa00015e,91276a7c9308ee41ec167575bf3036f3,uardkng4nw5zm1eonwi8fdwrwu2rkyze");
			put("meiren", "5ab4af438f4a9d6a5400027e,51020f8225fb687eeb5afbd2f2ec1069,vlxmwfy3sc5b8lzrsdokzv4wau4nrezq");
		}
	};
}
