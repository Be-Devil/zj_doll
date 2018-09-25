package com.imlianai.zjdoll.app.modules.publics.sms.xx;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.MD5Util;
public class Util {
	/**
	 * 请求地址
	 */
	public static String reqUrl = "http://sms.api.ypconsult.com/capi/sms.template";
	/**
	 * 商户号
	 */
	private static final String MERCHANT = "G394kvj1E4sN3Ik1";// 6JvDbP4GmdJk94J6
	/**
	 * 商户密钥
	 */
	private static final String MERCHANT_KEY = "OerJP1e5jFEntBKBx1ioKKwAbmMhOEOO";
	/**
	 * 版本号
	 */
	private static final String VERSION = "V100";

	public static Map<String, Object> getParam(long phone, Object code) {
		String base54Param = com.imlianai.rpc.support.utils.Base64
				.encode((code + "").getBytes());
		long timestamp = System.currentTimeMillis();
		StringBuffer signBuffer = new StringBuffer("merchant");
		String tempId = "86";
		signBuffer.append(MERCHANT);
		signBuffer.append("param");
		signBuffer.append(base54Param);
		signBuffer.append("phone");
		signBuffer.append(phone + "");
		signBuffer.append("templateId");
		signBuffer.append(tempId);
		signBuffer.append("timestamp");
		signBuffer.append(timestamp);
		signBuffer.append("version");
		signBuffer.append(VERSION);
		signBuffer.append(MERCHANT_KEY);
		System.out.println(signBuffer.toString());
		String md5Sign = MD5Util.MD5Encode(signBuffer.toString(), "UTF-8");
		System.out.println(md5Sign);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("merchant", MERCHANT);
		param.put("param", code + "");
		param.put("phone", phone + "");
		param.put("sign", md5Sign);
		param.put("templateId", tempId);
		param.put("timestamp", timestamp);
		param.put("version", VERSION);
		System.out.println(JSON.toJSON(param));
		return param;
	}
}
