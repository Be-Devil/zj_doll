package com.imlianai.zjdoll.app.modules.publics.oauth.weibo;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.user.UserOauth;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.publics.oauth.weibo.config.OauthWeiBoConfig;
import com.imlianai.zjdoll.app.modules.publics.qiniu.service.QiNiuService;

@Component
public class OauthWeiBoManagerImpl implements OauthWeiBoManager {

	private static final BaseLogger logger = BaseLogger
			.getLogger(OauthWeiBoManagerImpl.class);

	@Resource
	private QiNiuService qiNiuService;

	@Override
	public UserOauth getUserInfo(String access_token, String openId)
			throws OauthManagerException {
		if (StringUtils.isBlank(access_token)) {
			UserOauth userInfo = new UserOauth();
			userInfo.setErrmsg("login fail invalid accessToken");
			return userInfo;
		}
		if (StringUtils.isBlank(openId)) {
			UserOauth userInfo = new UserOauth();
			userInfo.setErrmsg("login fail invalid openId");
			return userInfo;
		}
		HttpEntity httpEntity = HttpUtil.Get(new StringBuffer(
				OauthWeiBoConfig.GET_USER_INFO_URL).append("?")
				.append("access_token=").append(access_token).append("&uid=")
				.append(openId).toString());
		String res = httpEntity.getHtml();
		UserOauth userInfo = new UserOauth(res, 2);
		String key = AppUtils.proNewName();
		if (StringUtils.isNotBlank(openId))
			key = openId;
		String url = qiNiuService.captureAndGetFile(userInfo.getHead(),
				OauthWeiBoConfig.BUCKET_NAME, OauthWeiBoConfig.KEY_FLAG + "/"
						+ key);
		if (StringUtils.isNotBlank(url)) {
			userInfo.setHead(url);
		}
		logger.info("auth-weibo:" + userInfo);
		return userInfo;
	}

}
