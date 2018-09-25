package com.imlianai.dollpub.app.modules.publics.oauth.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.dao.WechatDao;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WechatAccessToken;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WechatTicket;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinUserInfoHandler;
import com.imlianai.dollpub.domain.oauth.WechatAppSetting;
import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.dollpub.domain.user.UserWechat;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class WechatServiceImpl implements WechatService {
	protected final BaseLogger log = BaseLogger.getLogger(this.getClass());
	@Resource
	WechatDao wechatDao;

	@Override
	public void saveWechatUserInfo(String openId, String unionId, String name,
			String head) {
		wechatDao.saveWechatUserInfo(openId, unionId, name, head);
	}

	@Override
	public UserWechat getUserWechat(String unionId) {
		return wechatDao.getUserWechat(unionId);
	}

	@Override
	public void saveWechatUserInfo(UserWechat userWechat) {
		wechatDao.saveWechatUserInfo(userWechat.getOpenId(),
				userWechat.getUnionId(), userWechat.getName(),
				userWechat.getHead());
	}

	@Override
	public int addMpWeixinToken(String appid, String token, int expires,
			int type) {
		return wechatDao.addMpWeixinToken(appid, token, expires, type);
	}

	@Override
	public Map<Integer, WebWeixinToken> getWebTokenByAppid(String appid) {
		Map<Integer, WebWeixinToken> webToken = wechatDao.getWebToken(appid);
		if (webToken != null) {
			WebWeixinToken webWeixinToken = webToken
					.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
			if (webWeixinToken == null
					|| DateUtils.secondsBetweenNow(webWeixinToken
							.getUpdate_time()) > 6000) {
				updateWebWeixinToken(appid);
			}
			WebWeixinToken webWeixinTicket = webToken
					.get(WebWeixinTokenHandler.ACCESS_TICKET_TYPE);
			if (webWeixinTicket == null
					|| DateUtils.secondsBetweenNow(webWeixinTicket
							.getUpdate_time()) > 6000) {
				updateWebWeixinTicket(appid);
			}
		} else {
			updateWebWeixinToken(appid);
			updateWebWeixinTicket(appid);
		}
		webToken = wechatDao.getWebToken(appid);
		return webToken;
	}


	/**
	 * 
	 * 用于更新某个appid下的token
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	private void updateWebWeixinToken(String appid) {
		log.info("-------------------updateWebWeixinToken appid:" + appid);
		WechatAppSetting wechatAppSetting=getWechatAppSetting(appid);
		WechatAccessToken accessToken = WebWeixinTokenHandler.getToken(
				appid, wechatAppSetting.getAppsecret());
		WebWeixinToken webWeixinToken = new WebWeixinToken(
				appid,
				accessToken.getAccess_token(),
				accessToken.getExpires_in(),
				WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
		log.info("-------------------updateWebWeixinToken webWeixinToken:"
				+ webWeixinToken);
		addMpWeixinToken(appid, webWeixinToken.getToken_ticket(),
				webWeixinToken.getExpires_in(), webWeixinToken.getType());
		Map<Integer, WebWeixinToken> newTokenMap = new HashMap<Integer, WebWeixinToken>();
		newTokenMap.put(webWeixinToken.getType(), webWeixinToken);
		WebWeixinTokenHandler.setTokenMap(appid, newTokenMap);
		// 连同ticket一起更新
		// updateWebWeixinTicket(appid);
	}

	/**
	 * 用于更新某个appid下的Ticket
	 * 
	 * @param appid
	 */
	private void updateWebWeixinTicket(String appid) {
		log.info("-------------------updateWebWeixinTicket appid:" + appid);
		String token = null;
		Map<Integer, WebWeixinToken> webToken = wechatDao.getWebToken(appid);
		log.info("-------------------updateWebWeixinTicket webToken:"
				+ webToken);
		WebWeixinToken webWeixinToken = webToken
				.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
		if (webWeixinToken != null) {
			log.info("webWeixinToken!=null");
			token = webWeixinToken.getToken_ticket();
		} else {
			log.info("webWeixinToken==null");
			return;
		}
		WechatTicket ticket = WebWeixinJSSDKHandler.getTicket(token);
		if (ticket==null||StringUtil.isNullOrEmpty(ticket.getTicket())) {
			updateWebWeixinToken(appid);
			webToken = wechatDao.getWebToken(appid);
			token = webWeixinToken.getToken_ticket();
			ticket = WebWeixinJSSDKHandler.getTicket(token);
		}
		log.info("updateWebWeixinTicket ticket:" + ticket);
		webWeixinToken = new WebWeixinToken(appid,
				ticket.getTicket(),
				ticket.getExpires_in(),
				WebWeixinTokenHandler.ACCESS_TICKET_TYPE);
		addMpWeixinToken(appid, webWeixinToken.getToken_ticket(),
				webWeixinToken.getExpires_in(), webWeixinToken.getType());
		Map<Integer, WebWeixinToken> tokenMap = WebWeixinTokenHandler
				.getTokenMap(appid);
		if (tokenMap == null) {
			tokenMap = new HashMap<Integer, WebWeixinToken>();
		}
		webToken = wechatDao.getWebToken(appid);
		tokenMap.put(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE,
				webToken.get(WebWeixinTokenHandler.ACCESS_TOKEN));
		tokenMap.put(webWeixinToken.getType(), webWeixinToken);
		WebWeixinTokenHandler.setTokenMap(appid, tokenMap);
	}

	@Override
	public WechatAppSetting getWechatAppSetting(String appid) {
		return wechatDao.getWechatAppSetting(appid);
	}

	@Override
	public WechatAppSetting getWechatAppSettingBySrcid(String srcid) {
		return wechatDao.getWechatAppSettingBySrcid(srcid);
	}

	@Override
	public UserOauth getUserOauthBySrc(String srcid, String openId) {
		WechatAppSetting setting = getWechatAppSettingBySrcid(srcid);
		return getUserOauth(setting.getAppid(),openId);
	}

	@Override
	public UserOauth getUserOauth(String appid, String openId) {
		Map<Integer, WebWeixinToken> tokenMap = getWebTokenByAppid(appid);
		String accessToken = tokenMap.get(
				WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
		UserOauth userInfo = WebWeixinUserInfoHandler
				.getUserInfoMapByOpenID(accessToken, openId);
		if (!StringUtil.isNullOrEmpty(userInfo)&&!StringUtil.isNullOrEmpty(userInfo.getErrmsg())) {
			return getUserOauth(appid, openId);
		}
		return userInfo;
	}

}
