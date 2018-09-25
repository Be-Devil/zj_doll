package com.imlianai.zjdoll.app.modules.support.oauth.dao;
import com.imlianai.zjdoll.app.modules.support.oauth.domain.WeiXinUser;
public interface OauthDao {
	/**
	 * 添加微信记录
	 * 
	 * @param user
	 * @return
	 */
	public int addWeiXinUser(WeiXinUser user);
}
