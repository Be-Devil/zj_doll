package com.imlianai.zjdoll.app.modules.publics.weixinAccount.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinUserInfoHandler;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.dao.WebWeixinDAO;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.OauthUserBean;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebUserInfoToken;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebWeixinUserInfo;

@Service
public class WebWeixinServiceImpl implements WebWeixinService {

	@Resource
	private WebWeixinDAO webWeixinDAO;

	private final Logger log = Logger.getLogger(WebWeixinServiceImpl.class);

	@Override
	public int addMpWeixinToken(String appid, String token, int expires,
			int type) {
		return webWeixinDAO.addMpWeixinToken(appid, token, expires, type);
	}

	@Override
	public Map<Integer, WebWeixinToken> getWebTokenByAppid(String appid) {
		Map<Integer, WebWeixinToken> webToken = webWeixinDAO.getWebToken(appid);
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
		webToken = webWeixinDAO.getWebToken(appid);
		return webToken;
	}

	public Map<Integer, WebWeixinToken> getWebTokenByAppidOld(String appid) {
		log.info("--------------getWebTokenByAppid appid:" + appid);
		if (WebWeixinTokenHandler.getTokenMap() == null) {
			log.info("--------------WebWeixinTokenHandler.getTokenMap()==null");
			Map<Integer, WebWeixinToken> webToken = webWeixinDAO
					.getWebToken(appid);
			log.info("--------------getWebTokenByAppid webToken:" + webToken);
			if (webToken != null) {
				log.info("--------------getWebTokenByAppid webToken!=null webToken"
						+ webToken);
				WebWeixinToken webWeixinToken = webToken
						.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
				WebWeixinToken webWeixinTicket = webToken
						.get(WebWeixinTokenHandler.ACCESS_TICKET_TYPE);
				if (webWeixinToken != null
						&& DateUtils.secondsBetweenNow(webWeixinToken
								.getUpdate_time()) < (webWeixinToken
								.getExpires_in() - 60)
						&& webWeixinTicket != null
						&& DateUtils.secondsBetweenNow(webWeixinTicket
								.getUpdate_time()) < (webWeixinTicket
								.getExpires_in() - 60)) {
					// 设置入内存
					Map<Integer, WebWeixinToken> newTokenMap = new HashMap<Integer, WebWeixinToken>();
					newTokenMap.put(webWeixinToken.getType(), webWeixinToken);
					newTokenMap.put(webWeixinTicket.getType(), webWeixinTicket);
					WebWeixinTokenHandler.setTokenMap(appid, newTokenMap);
					log.info("----------------WebWeixinTokenHandler:"
							+ WebWeixinTokenHandler.getTokenMap());
					log.info("----------------WebWeixinTokenHandler:"
							+ WebWeixinTokenHandler.getTokenMap().get(appid));
				} else {
					updateWebWeixinToken(appid);
				}
				return WebWeixinTokenHandler.getTokenMap().get(appid);
			}
			log.info("--------------getWebTokenByAppid later");
			updateWebWeixinToken(appid);
		} else {
			log.info("WebWeixinTokenHandler.getTokenMap()!=null");
			// 判断是否是已经过期
			Map<Integer, WebWeixinToken> map = WebWeixinTokenHandler
					.getTokenMap().get(appid);
			WebWeixinToken webWeixinToken = map
					.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
			WebWeixinToken webWeixinTicket = map
					.get(WebWeixinTokenHandler.ACCESS_TICKET_TYPE);
			if (webWeixinToken != null && webWeixinTicket != null) {
				if (DateUtils
						.secondsBetweenNow(webWeixinToken.getUpdate_time()) < (webWeixinToken
						.getExpires_in() - 60)
						&& DateUtils.secondsBetweenNow(webWeixinTicket
								.getUpdate_time()) < (webWeixinTicket
								.getExpires_in() - 60)) {
					return WebWeixinTokenHandler.getTokenMap().get(appid);
				}
			}
			updateWebWeixinToken(appid);
		}
		log.info("----------------WebWeixinTokenHandler:"
				+ WebWeixinTokenHandler.getTokenMap().get(appid));
		return WebWeixinTokenHandler.getTokenMap().get(appid);
	}

	/**
	 * 
	 * 用于更新某个appid下的token
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	private void updateWebWeixinToken(String appid) {
		log.info("-------------------updateWebWeixinToken appid:" + appid);
		Map<String, Object> accessTokenMap = WebWeixinTokenHandler.getToken(
				appid, WebWeixinTokenHandler.getAppSecretByAppID(appid));
		WebWeixinToken webWeixinToken = new WebWeixinToken(
				appid,
				(String) accessTokenMap.get(WebWeixinTokenHandler.ACCESS_TOKEN),
				(Integer) accessTokenMap.get(WebWeixinTokenHandler.EXPIRES_IN),
				WebWeixinTokenHandler.ACCESS_TOKEN_TYPE);
		log.info("-------------------updateWebWeixinToken webWeixinToken:"
				+ webWeixinToken);
		webWeixinDAO.addMpWeixinToken(appid, webWeixinToken.getToken_ticket(),
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
		Map<Integer, WebWeixinToken> webToken = webWeixinDAO.getWebToken(appid);
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
		Map<String, Object> ticketMap = WebWeixinJSSDKHandler.getTicket(token);
		log.info("updateWebWeixinTicket ticketMap:" + ticketMap);
		webWeixinToken = new WebWeixinToken(appid,
				(String) ticketMap.get(WebWeixinJSSDKHandler.access_ticket),
				(Integer) ticketMap.get(WebWeixinTokenHandler.EXPIRES_IN),
				WebWeixinTokenHandler.ACCESS_TICKET_TYPE);
		log.info("-------------------updateWebWeixinTicket ticketMap:"
				+ ticketMap);
		webWeixinDAO.addMpWeixinToken(appid, webWeixinToken.getToken_ticket(),
				webWeixinToken.getExpires_in(), webWeixinToken.getType());
		Map<Integer, WebWeixinToken> tokenMap = WebWeixinTokenHandler
				.getTokenMap(appid);
		if (tokenMap == null) {
			tokenMap = new HashMap<Integer, WebWeixinToken>();
		}
		webToken = webWeixinDAO.getWebToken(appid);
		tokenMap.put(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE,
				webToken.get(WebWeixinTokenHandler.ACCESS_TOKEN));
		tokenMap.put(webWeixinToken.getType(), webWeixinToken);
		WebWeixinTokenHandler.setTokenMap(appid, tokenMap);
	}

	@Override
	public int addMpWeixinUserInfoToken(String openid, String access_token,
			String refresh_token, int expire_in) {
		return webWeixinDAO.addMpWeixinUserInfoToken(openid, access_token,
				refresh_token, expire_in);
	}

	@Override
	public WebUserInfoToken getUserInfoToken(String openid) {
		return webWeixinDAO.getUserInfoToken(openid);
	}

	@Override
	public int addWeixinUserInfo(WebWeixinUserInfo info) {
		return webWeixinDAO.addWeixinUserInfo(info);
	}

	@Override
	public int addWeixinUserInfo(String appid, WebWeixinUserInfo info) {
		return webWeixinDAO.addWeixinUserInfo(appid, info);
	}

	@Override
	public WebWeixinUserInfo getWeixinUserInfo(String openid) {
		return webWeixinDAO.getWeixinUserInfo(openid);
	}

	@Override
	public WebWeixinUserInfo getWeixinUserInfo(String appid, String openid) {
		return webWeixinDAO.getWeixinUserInfo(appid, openid);
	}

	@Override
	public boolean isExitUserInfo(String openid) {
		return webWeixinDAO.isExitUserInfo(openid);
	}

	@Override
	public boolean isExitUserInfo(String appid, String openid) {
		return webWeixinDAO.isExitUserInfo(appid, openid);
	}

	@Override
	public OauthUserBean getWechatUserInfo(String appid, String openid) {
		Map<Integer,WebWeixinToken> tokenMap = getWebTokenByAppid(appid);
		String accessToken =tokenMap.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
		Map<String, Object> map=WebWeixinUserInfoHandler.getUserInfoMapByOpenID(accessToken, openid);
		try {
			OauthUserBean user=new OauthUserBean(JSON.toJSONString(map), 3);
			return user;
		} catch (OauthManagerException e) {
			PrintException.printException(log, e);
		}
		return null;
	}

}
