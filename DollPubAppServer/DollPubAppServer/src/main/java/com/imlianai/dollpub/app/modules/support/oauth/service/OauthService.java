package com.imlianai.dollpub.app.modules.support.oauth.service;

import com.imlianai.dollpub.app.modules.support.oauth.domain.WeiXinUser;

public interface OauthService {

	/**
	 * 添加微信记录
	 * 
	 * @param user
	 * @return
	 */
	public int addWeiXinUser(WeiXinUser user);
}
