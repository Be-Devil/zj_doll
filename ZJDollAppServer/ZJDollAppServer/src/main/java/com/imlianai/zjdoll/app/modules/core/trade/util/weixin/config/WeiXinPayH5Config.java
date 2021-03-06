package com.imlianai.zjdoll.app.modules.core.trade.util.weixin.config;

import com.ctrip.framework.apollo.util.PropertiesUtil;

/**
 * 直播公众号支付配置（与app内微信充值不同账户）
 * @author tensloveq
 *
 */
public class WeiXinPayH5Config {

	/**
	 * 统一下单url
	 */
	public static final String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 公众账号ID
	 */
	public static final String appId ="wx8c5308361b165de9";// PropertiesUtil.getString("application","weixinH5.appId");// "wxc68eccf842b1eeb0";//"wx72f7d35891f70172";//PropertiesUtil.getString("application","weixinH5.appId");
	/**
	 * 商户号
	 */
	public static final String mchId ="1496421812";//PropertiesUtil.getString("application","weixinH5.mchId");//"1495379632";//"1458868102";// PropertiesUtil.getString("application","weixinH5.mchId");

	/**
	 * js回调地址
	 */
	public static final String jsNotifyUrl=PropertiesUtil.getString("application","weixinH5.notifyUrl");
	/**
	 * 回调地址
	 */
	//public static final String notifyUrl = "http://t.xiehou360.com/LiveWebServer/weixinpay.do";
	public static final String notifyUrl=PropertiesUtil.getString("application","weixinH5.notifyUrl");

	/**
	 * apikey
	 */
	public static final String apiKey = PropertiesUtil.getString("application","weixinH5.apiKey");

}
