package com.imlianai.dollpub.app.modules.publics.oauth.wechat.dao;

import java.util.Map;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.domain.oauth.WechatAppSetting;
import com.imlianai.dollpub.domain.user.UserWechat;

public interface WechatDao {

	/**
	 * 保存微信用户信息
	 * @param openId
	 * @param unionId
	 * @param name
	 * @param head
	 */
	public void saveWechatUserInfo(String openId,String unionId,String name,String head);
	
	/**
	 * 获取微信用户信息
	 * @param unionId
	 * @return
	 */
	public UserWechat getUserWechat(String unionId);
	
	
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
	 * 获取app信息
	 * @param appid
	 * @return
	 */
	public WechatAppSetting getWechatAppSetting(String appid);
	
	/**
	 * 获取app信息
	 * @param srcid
	 * @return
	 */
	public WechatAppSetting getWechatAppSettingBySrcid(String srcid);
}
