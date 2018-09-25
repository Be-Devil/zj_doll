package com.imlianai.dollpub.app.modules.core.trade.util.weixin.config;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.LivePropsUtil;

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
	public static final String appId = PropertiesUtil.getString("application","weixinH5.appId");
	/**
	 * 商户号
	 */
	public static final String mchId = PropertiesUtil.getString("application","weixinH5.mchId");

	/**
	 * 回调地址
	 */
	//public static final String notifyUrl = "http://t.xiehou360.com/LiveWebServer/weixinpay.do";
	public static final String notifyUrl=PropertiesUtil.getString("application","weixinH5.notifyUrl");

	/**
	 * 公众号支付回调地址
	 */
	public static final String jsNotifyUrl=PropertiesUtil.getString("application","weixinH5.notifyUrl");
	/**
	 * apikey
	 */
	public static final String apiKey = PropertiesUtil.getString("application","weixinH5.apiKey");

}
