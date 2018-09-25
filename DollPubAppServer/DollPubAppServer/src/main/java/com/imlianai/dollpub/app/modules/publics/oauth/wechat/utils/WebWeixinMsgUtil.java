package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WechatTemplateMsg;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WechatTemplateMsgRes;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 微信消息
 * @author tensloveq
 *
 */
public class WebWeixinMsgUtil {

	private final static Logger logger = Logger
			.getLogger(WebWeixinMsgUtil.class);
	private static final String send_message = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	public static WechatTemplateMsgRes sendTemplateMsg(WechatTemplateMsg msg,String token){
		String urlString=getTemplateMsgUrl(token);
		HttpEntity httpEntity=HttpUtil.Post(urlString, JSON.toJSONString(msg));
		if (httpEntity!=null&&!StringUtil.isNullOrEmpty(httpEntity.getHtml())) {
			logger.info("sendTemplateMsg msg:"+JSON.toJSONString(msg)+" token:"+token+" urlString:"+urlString+" httpEntity:"+JSON.toJSONString(httpEntity));
			WechatTemplateMsgRes res=JSON.parseObject(httpEntity.getHtml(), WechatTemplateMsgRes.class);
			return res;
		}
		return null;
	}
	
	private static String getTemplateMsgUrl(String token){
		return String.format(send_message, token);
	}
}
