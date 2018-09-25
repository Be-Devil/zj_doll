package com.imlianai.dollpub.app.modules.core.trade.util.weixin.config;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.LivePropsUtil;

public class WeiXinPayConfig {

	/**
	 * 统一下单url
	 */
	public static final String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 公众账号ID
	 */
	public static final String appId = PropertiesUtil.getString("application","weixin.appId");// "wx502d95fb39a85016";

	/**
	 * 商户号
	 */
	public static final String mchId = PropertiesUtil.getString("application","weixin.mchId");//"1493137162";

	/**
	 * 回调地址
	 */
	public static final String notifyUrl = PropertiesUtil.getString("application","weixin.notifyUrl");// "http://122.11.48.251:6639/DollAppServer/api/back/paywx/doBack";
	/**
	 * 测试回调地址
	 */
	public static final String notifyUrl_test = "http://122.11.48.251:6639/DollAppServer/api/back/paywx/doBack";
	/**
	 * apikey
	 */
	public static final String apiKey = PropertiesUtil.getString("application","weixin.apiKey");// "guangzhoulianaiwangluomishuo6666";

}
