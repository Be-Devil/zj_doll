package com.imlianai.zjdoll.app.modules.core.trade.util.weixin.config;

import com.ctrip.framework.apollo.util.PropertiesUtil;

/**
 * 直播公众号支付配置（与app内微信充值不同账户）
 * @author tensloveq
 *
 */
public class WeiXinPayJSConfig {

	/**
	 * 统一下单url
	 */
	public static final String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 公众账号ID
	 */
	public static final String appId = PropertiesUtil.getString("application","weixinJS.appId");//"wxc68eccf842b1eeb0";//"wx72f7d35891f70172";//PropertiesUtil.getString("application","weixinH5.appId");
	/**
	 * 商户号
	 */
	public static final String mchId =PropertiesUtil.getString("application","weixinJS.mchId");//"1495379632";//"1458868102";// PropertiesUtil.getString("application","weixinH5.mchId");
	
	/**
	 * js回调地址
	 */
	public static final String jsNotifyUrl=PropertiesUtil.getString("application","weixinJS.notifyUrl");
	/**
	 * 回调地址
	 */
	//public static final String notifyUrl = "http://t.xiehou360.com/LiveWebServer/weixinpay.do";
	public static final String notifyUrl=PropertiesUtil.getString("application","weixinJS.notifyUrl");

	/**
	 * apikey
	 */
	public static final String apiKey =PropertiesUtil.getString("application","weixinJS.apiKey");//"wx72f7d35891f70172LianlianXiehou";//

}
