package com.imlianai.dollpub.app.modules.support.idfa.util;
public class IdfaUtil {

	private static final String appId = "1229070443";

	/**
	 * 卓泰回调url
	 * 
	 * @param idfa
	 * @param ip
	 * @return
	 */
	public static String getZtUrl(String idfa, String ip) {
		String url = "http://data.zttx.net/ztact/act.php?appid=%s&idfa=%s&actip=%s&acttime=%s";
		long acttime = System.currentTimeMillis() / 1000;
		return String.format(url, appId, idfa, ip, acttime);
	}

	public static void main(String[] args) {
		System.out.println(getZtUrl("dddxxx", "127.0.0.1"));
	}

}
