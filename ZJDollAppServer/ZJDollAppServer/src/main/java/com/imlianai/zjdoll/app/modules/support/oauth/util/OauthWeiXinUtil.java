package com.imlianai.zjdoll.app.modules.support.oauth.util;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.imlianai.zjdoll.domain.user.OsType;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.support.oauth.cnf.OauthWeiXinCnf;

public class OauthWeiXinUtil {

	/**
	 * 获取微信请求code
	 * 
	 * @param request
	 * @param redirectParam
	 * @return
	 */
	public static String getCodeUrl(Map<String, String> redirectParam) {
		StringBuffer locationBuffer = new StringBuffer(AppUtils.getDomainName());
		locationBuffer.append("/api/back/oauthWeiXin/oauth");
		String location = locationBuffer.toString();
		String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		String paramStr = AppUtils.buildQuery(redirectParam);
		if (StringUtils.isNotBlank(paramStr))
			location = location + "?" + paramStr;
		return MessageFormat.format(codeUrl, OauthWeiXinCnf.APP_ID, location);
	}

	/**
	 * 获取token
	 * 
	 * @param code
	 * @return
	 */
	public static String getTokenUrl(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		url = MessageFormat.format(url, OauthWeiXinCnf.APP_ID,
				OauthWeiXinCnf.SECRET, code);
		return url;
	}

	/**
	 * 获取用户信息url
	 * 
	 * @param token
	 * @param openId
	 * @return
	 */
	public static String getUserInfoUrl(String token, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
		return MessageFormat.format(url, token, openId);
	}

	/**
	 * 获取设备类型
	 * 
	 * @param request
	 * @return
	 */
	public static OsType getOsType(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		OsType osType = null;
		if (StringUtils.indexOfIgnoreCase(userAgent, "iPhone") > 0
				|| StringUtils.indexOfIgnoreCase(userAgent, "Mac") > 0
				|| StringUtils.indexOfIgnoreCase(userAgent, "ios") > 0) {
			osType = OsType.IOS;
		} else {
			osType = OsType.ANDROID;
		}
		return osType;
	}
}
