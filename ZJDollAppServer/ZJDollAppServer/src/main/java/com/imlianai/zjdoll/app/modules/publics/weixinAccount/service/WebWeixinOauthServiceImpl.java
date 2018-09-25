package com.imlianai.zjdoll.app.modules.publics.weixinAccount.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.dao.OauthWeixinDao;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinShareOauthUtil;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinUserInfoHandler;

@Service
public class WebWeixinOauthServiceImpl implements WebWeixinOauthService {

	final static BaseLogger logger = BaseLogger
			.getLogger(WebWeixinOauthServiceImpl.class);
	@Resource
	private WebWeixinService webWeixinService;
	@Resource
	private OauthWeixinDao oauthWeixinDao;

	@Override
	public String getUnionIdByOpenId(String appid, String openId) {
		Map<Integer, WebWeixinToken> tokenMap = webWeixinService
				.getWebTokenByAppid(appid);
		String accessToken = tokenMap.get(
				WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
		Map<String, Object> user = WebWeixinUserInfoHandler
				.getUserInfoMapByOpenID(accessToken, openId);
		return (String) user.get("unionid");
	}

	@Override
	public Long getUidByUnionId(String unionId) {
		Long uid = oauthWeixinDao.getBondUidByUnionId(unionId);
		return uid;
	}

	public static void main(String[] args) {
		String openId = "o4LbFvu6ZB0UzW1rXHdH55nkXJfk";
		String accessToken = "HBVynOtzCthZ3t9sEMhGUexed26kolR2YmpOMjBdUfk4DuhJm6hXFAHKTiRqpueFnvieFowtd10wwiVLeh__7Ow9STuTlrWT7Ka-7bGKpcquYFHcH0vPm4FJlHbYY5CCUUAcCFAXXD";
		Map<String, Object> user = WebWeixinUserInfoHandler
				.getUserInfoMapByOpenID(accessToken, openId);
		System.out.println(user);
	}

	@Override
	public UserWechat getUserInfoWeb(String appid, String userCode)
			throws OauthManagerException {
		UserWechat res = WebWeixinShareOauthUtil.getUserInfoByCode(appid,
				WebWeixinTokenHandler.getAppSecretByAppID(appid), userCode);
		logger.info("auth-weixin:" + JSON.toJSONString(res));
		return res;
	}
}
