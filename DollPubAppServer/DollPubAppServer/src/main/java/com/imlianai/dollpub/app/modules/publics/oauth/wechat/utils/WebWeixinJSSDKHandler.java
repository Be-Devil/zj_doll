package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WechatTicket;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.HttpUtil;

public class WebWeixinJSSDKHandler {

	private static final BaseLogger logger = BaseLogger
			.getLogger(WebWeixinJSSDKHandler.class);

	private static Map<String, Object> ticketMap = null;

	public static String access_ticket = "ticket";

	private static final String expires_in = "expires_in";
/*
	private static final String AppID = "wxb3cc96d677f920d0";
	private static final String AppSecret = "93d65424ac74b99b9829c822b52427ca";
	*/
	
//	private static final String AppID = "wxb8d65bba2a9956c6";
//	
//	private static final String AppSecret ="1472c67ee6abb808ff33ac5fa75f23aa";
	 
	private static long lastGetTicketTime = 0;

	private static final String get_ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	/**
	 * 
	 * 获取tokenMap
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Map<String, Object> getTicketMap() {
		if (ticketMap == null) {
			// 发起请求获取
			getTicket((String) WebWeixinTokenHandler.getTokenMap().get(
					WebWeixinTokenHandler.ACCESS_TOKEN).get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket());
		} else {
			// 判断是否已超时
			if (System.currentTimeMillis() - lastGetTicketTime > Long
					.parseLong(ticketMap.get(expires_in).toString()) * 1000) {
				getTicket((String) WebWeixinTokenHandler.getTokenMap().get(
						WebWeixinTokenHandler.ACCESS_TOKEN).get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket());
			}
		}
		return ticketMap;
	}

	/**
	 * 通用接口获取Token凭证
	 * 
	 * @param token
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static WechatTicket getTicket(String token) {
		String ticket = "";
		if (token == null) {
			return null;
		}
		String ticketUrl = WebWeixinJSSDKHandler.getTicketUrl(token);
		String response = "";
		WechatTicket wechatTicket=null;
		// 共3次获取token，成功一次则跳出
		response = HttpUtil.Get(ticketUrl).getHtml();
		System.out.println("ticket info:" + response);
		if (null != response && !response.trim().equals("")) {
			wechatTicket=JSON.parseObject(response, WechatTicket.class);
		}
		lastGetTicketTime = System.currentTimeMillis();
		System.out.println("lastGetTicketTime:" + lastGetTicketTime);
		return wechatTicket;
	}

	public static String getTicketUrl(String token) {
		return String.format(get_ticket, token);
	}

	public static Map<String, String> sign(String appid,String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appid);
		logger.info("url"+ url);
		logger.info("jsapi_ticket"+ jsapi_ticket);
		logger.info("nonceStr"+ nonce_str);
		logger.info("timestamp"+ timestamp);
		logger.info("signature"+ signature);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
