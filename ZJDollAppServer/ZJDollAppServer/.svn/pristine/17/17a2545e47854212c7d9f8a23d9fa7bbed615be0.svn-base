package com.imlianai.zjdoll.app.modules.publics.qiniu.pili;

public class QNHandle {
	/**
	 * 推流地址
	 */
	private static final String push_domain="pili-publish.xiehou360.com";

	/**
	 * 直播空间
	 */
	private static final String live_space="suanguolive";
	
	/**
	 * 获得推流地址
	 * @param streamName
	 * @return
	 */
	public static String getPushUrl(String streamName){
		String pushUrl=Client.RTMPPublishURL(push_domain,
				live_space, streamName, 60*60*12);
		return pushUrl;
	}
}
