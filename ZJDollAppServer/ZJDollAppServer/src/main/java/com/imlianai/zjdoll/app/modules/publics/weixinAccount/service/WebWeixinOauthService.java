package com.imlianai.zjdoll.app.modules.publics.weixinAccount.service;

import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;

public interface WebWeixinOauthService {

	/**
	 * 根据appid与openId获取unionId
	 * @param appid
	 * @param openId
	 * @return
	 */
	public String getUnionIdByOpenId(String appid,String openId);
	
	/**
	 * 根据unionId获取uid
	 * @param unionId
	 * @return
	 */
	public Long getUidByUnionId(String unionId);
	
	/**
	 *  获取用户信息--用于微信内授权
	 * @param appid
	 * @param userCode
	 * @return
	 * @throws OauthManagerException
	 */
	UserWechat getUserInfoWeb(String appid, String userCode)
			throws OauthManagerException;
}
