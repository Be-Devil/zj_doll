package com.imlianai.dollpub.app.modules.publics.oauth.qq;

import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.rpc.support.common.exception.OauthManagerException;

public interface OauthQQManager {

	/**
	 * 获取用户信息(token有效)
	 * 
	 * 注:当errmsg信息不为空:校验失败
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws OauthManagerException
	 * @throws
	 */
	UserOauth getUserInfo(String accessToken, String openId)
			throws OauthManagerException;

}
