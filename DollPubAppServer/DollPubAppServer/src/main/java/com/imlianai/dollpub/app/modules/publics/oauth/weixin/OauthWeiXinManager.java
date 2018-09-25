package com.imlianai.dollpub.app.modules.publics.oauth.weixin;
import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.rpc.support.common.exception.OauthManagerException;

public interface OauthWeiXinManager {

	/**
	 * 获取用户信息(校验token)
	 * 
	 * 注:当errmsg信息不为空:校验失败
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws OauthWeiXinManagerException
	 * @throws OauthManagerException
	 */
	UserOauth getUserInfo(String accessToken, String openId)
			throws OauthManagerException;
	
	/**
	 * 获取公众号用户信息
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	UserOauth getUserInfo(String appid,String appSecret, String code);

}
