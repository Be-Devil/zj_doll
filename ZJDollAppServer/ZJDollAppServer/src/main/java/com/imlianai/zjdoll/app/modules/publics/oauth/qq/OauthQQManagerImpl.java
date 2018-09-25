package com.imlianai.zjdoll.app.modules.publics.oauth.qq;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.user.UserOauth;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.publics.oauth.qq.config.OauthQQConfig;
import com.imlianai.zjdoll.app.modules.publics.qiniu.service.QiNiuService;

@Component
public class OauthQQManagerImpl implements OauthQQManager {

	private static final Logger logger = Logger
			.getLogger(OauthQQManagerImpl.class);

	@Resource
	private QiNiuService qiNiuService;

	@Override
	public UserOauth getUserInfo(String access_token, String openId)
			throws OauthManagerException {
		logger.info("OauthQQManager access_token" + access_token + " openId:"
				+ openId);
		HttpEntity httpEntity = HttpUtil.Get(
				new StringBuffer(OauthQQConfig.GET_USER_INFO_URL).append("?")
						.append("oauth_consumer_key=")
						.append(OauthQQConfig.APP_ID).append("&access_token=")
						.append(access_token).append("&openid=").append(openId)
						.toString(), "utf-8");
		String res = httpEntity.getHtml();
		logger.info("getUserInfo res:" + res);
		UserOauth userInfo = new UserOauth(res, 1);
		userInfo.setOpenId(openId);
		logger.info("auth-qq:" + userInfo);
		String key = AppUtils.proNewName();
		if (StringUtils.isNotBlank(openId))
			key = openId;
		String url = qiNiuService.captureAndGetFile(userInfo.getHead(),
				OauthQQConfig.BUCKET_NAME, OauthQQConfig.KEY_FLAG + "/" + key);
		if (StringUtils.isNotBlank(url)) {
			userInfo.setHead(url);
		}
		return userInfo;
	}

	public static void main(String[] args) {
		String openId = "0841CEFB965D344977CD860706835560";
		String access_token = "0E18324FC483F99FA97BC990720EB538";
		try {
			new OauthQQManagerImpl().getUserInfo(access_token, openId);
		} catch (OauthManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
