package com.imlianai.dollpub.app.modules.publics.oauth.weibo;

import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.rpc.support.common.exception.OauthManagerException;

public interface OauthWeiBoManager {

	/**
	 * @throws OauthManagerException
	 *             获取用户信息(校验token)
	 * 
	 *             注:当errmsg信息不为空:校验失败
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws
	 */
	UserOauth getUserInfo(String accessToken, String openId) throws OauthManagerException;

}
