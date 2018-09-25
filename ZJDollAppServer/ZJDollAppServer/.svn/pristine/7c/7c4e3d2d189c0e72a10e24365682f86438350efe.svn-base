package com.imlianai.zjdoll.app.modules.publics.weixinAccount.dao;

import java.util.Map;

import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebUserInfoToken;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebWeixinUserInfo;



/**
 * 公众号授权相关
 * @author tensloveq
 *
 */
public interface WebWeixinDAO {
	
	
	/**
	 * 添加access_token到数据库
	 * @param token
	 * @param expires
	 * @param type
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public int addMpWeixinToken(String appid,String token,int expires, int type);
	
	/**
	 * 根据appid获取Web AccessToken
	 * @return
	 */
	public Map<Integer,WebWeixinToken> getWebToken(String appid);
	/**
	 * 保存用户token信息
	 * @param openid
	 * @param access_token
	 * @param refresh_token
	 * @param expire_in
	 * @return
	 */
	public int addMpWeixinUserInfoToken(String openid,String access_token,String refresh_token,int expire_in);
	
	/**
	 * 获取用户的token信息
	 * @param openid
	 * @return
	 */
	public WebUserInfoToken getUserInfoToken(String openid);
	
	/**
	 * 保存微信用户信息
	 * @param info
	 * @return
	 */
	public int addWeixinUserInfo(WebWeixinUserInfo info);
	
	/**
	 * 保存微信用户信息
	 * @param info
	 * @return
	 */
	public int addWeixinUserInfo(String appid,WebWeixinUserInfo info);
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 */
	public WebWeixinUserInfo getWeixinUserInfo(String openid);
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 */
	public WebWeixinUserInfo getWeixinUserInfo(String appid,String openid);
	
	/**
	 * 是否已经存在该用户
	 * @param openid
	 * @return
	 */
	public boolean isExitUserInfo(String openid);
	
	/**
	 * 是否已经存在该用户
	 * @param openid
	 * @return
	 */
	public boolean isExitUserInfo(String appid,String openid);
}
