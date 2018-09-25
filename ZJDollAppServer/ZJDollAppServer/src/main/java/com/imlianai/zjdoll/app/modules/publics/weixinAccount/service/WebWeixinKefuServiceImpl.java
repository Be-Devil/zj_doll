package com.imlianai.zjdoll.app.modules.publics.weixinAccount.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;

@Service
public class WebWeixinKefuServiceImpl implements WebWeixinKefuService {

	@Resource
	WebWeixinService webWeixinService;
	
	private final static BaseLogger logger = BaseLogger
			.getLogger(WebWeixinKefuServiceImpl.class);
	
	private static final String send_kefu_message = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

	public static String getSendKefuMessageUrl(String token) {
		return String.format(send_kefu_message, token);
	}

	@Override
	public String sendKefuMessage(String receviedUser,String senderUser, String text,int type) {
		sendKefuMessageApi(receviedUser, senderUser, text);
		return null;
	}

	/**
	 * 发送消息到微信服务器
	 * @param token
	 * @param text
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private void sendKefuMessageApi(String receviedUser,String senderUser, String text) {
		//消息体json串
		String messageJsonString = getTextMessageJsonString(receviedUser, text);
		//获取发送消息公众号token
		String token=webWeixinService.getWebTokenByAppid(senderUser).get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
		//发送地址
		String messageUrlString= getSendKefuMessageUrl(token);
		String response = "";
		// 共3次获取token，成功一次则跳出
		for (int i = 0; i <= 3; i++) {
			response =HttpUtil.Post(messageUrlString, messageJsonString).getHtml();
			logger.info("getToken the " + i + 1 + " time " + response);
			if (null != response && !response.trim().equals("")) {
				logger.info("sendKefuMessageApi"+response);
				break;
			}
		}
		logger.info("messageJsonString:"+messageJsonString);
		logger.info("token:"+token);
	}

	/**
	 * 获取文本消息内容
	 * @param openId 消息接收方openId
	 * @param text 消息内容
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private String getTextMessageJsonString(String openId,String text) {
		String jsonString = null;
		// 获取第三发ticket
		try {
			Map<String, Object> tokenRequestMap = new HashMap<String, Object>();
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("content",text);
			tokenRequestMap.put("touser", openId);
			tokenRequestMap.put("msgtype", "text");
			tokenRequestMap.put("text",
					contentMap);
			jsonString = JSON.toJSONString(tokenRequestMap);
			logger.info("文本消息 jsonString=" + jsonString);
		} catch (Exception e) {
			logger.info(" 无法获取文本消息 Post字符串");
			logger.error(" 无法获取文本消息 Post字符串", e);
			return null;
		}
		return jsonString;
	}

}
