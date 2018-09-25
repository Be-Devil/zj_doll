package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

import java.util.Random;

import com.imlianai.dollpub.app.modules.core.trade.util.common.MD5Util;

/**
 * 微信通用工具类
 * @author tensloveq
 *
 */
public class WebWeixinCommonUtils {
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}
