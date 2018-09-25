package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.utils.HttpUtil;

/**
 * 微信用户资料处理
 * @author tensloveq
 *
 */
public class WebWeixinUserInfoHandler {

	/**
	 * 根据openId获取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static Map<String, Object> getUserInfoMapByOpenID(
			String accessToken, String openid) {
		StringBuffer url = new StringBuffer(
				"https://api.weixin.qq.com/cgi-bin/user/info?");
		url.append("access_token=");
		url.append(accessToken);
		url.append("&openid=");
		url.append(openid);
		url.append("&lang=zh_CN");
		System.out.println(url.toString());
		String userInfoJSON = HttpUtil.Get(url.toString()).getHtml();
		//logger.info(userInfoJSON);
		if (StringUtils.isNotBlank(userInfoJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(userInfoJSON, Map.class);
			return map;
		}
		return null;
	}
	
	public static void main(String[] args) {
		String accessToken="aB7h-W1ReUa_rFuVzHkwR4SgEsBRILwz5UxCJueCrhVeaNaeLR5xX4Eh9lmCJEtp6d9-x2hvByt2FjcBroLaIkTkrrwYRw8keyCpkNg17PLYglDC7U529G0Aby3dv9ysXZQhAEALNE";
		String openid="onk6BtxTZLHfOw2bp2Wu-f-AWkww";
		System.out.println(WebWeixinUserInfoHandler.getUserInfoMapByOpenID(accessToken, openid));
	}
	
	/**
	 * 小程序获取用户信息
	 * @param jsCode
	 * @return
	 */
	public static Map<String, Object> getUserInfoMapByJsCode(
			String jsCode) {
		StringBuffer url = new StringBuffer(
				"https://api.weixin.qq.com/sns/jscode2session?");
		url.append("appid=");
		url.append(WechatProjectCommonUtil.appid);
		url.append("&secret=");
		url.append(WechatProjectCommonUtil.secret);
		url.append("&js_code=");
		url.append(jsCode);
		url.append("&grant_type=authorization_code");
		System.out.println(url.toString());
		String userInfoJSON = HttpUtil.Get(url.toString()).getHtml();
		if (StringUtils.isNotBlank(userInfoJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(userInfoJSON, Map.class);
			return map;
		}
		return null;
	}
}
