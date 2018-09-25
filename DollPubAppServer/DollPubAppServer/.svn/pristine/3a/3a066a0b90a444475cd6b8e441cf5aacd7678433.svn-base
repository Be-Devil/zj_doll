package com.imlianai.dollpub.app.modules.publics.oauth.wechat.service;

import java.util.Map;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.domain.oauth.WechatAppSetting;
import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.dollpub.domain.user.UserWechat;

public interface WechatService {

	/**
	 * 保存微信用户信息
	 * @param openId
	 * @param unionId
	 * @param name
	 * @param head
	 */
	public void saveWechatUserInfo(String openId,String unionId,String name,String head);
	
	/**
	 * 保存微信用户信息
	 * @param userWechat
	 */
	public void saveWechatUserInfo(UserWechat userWechat);
	/**
	 * 获取微信用户信息
	 * @param unionId
	 * @return
	 */
	public UserWechat getUserWechat(String unionId);
	
	/**
	 * 根据公众号srcid与openId获取授权信息
	 * @param srcid
	 * @param openId
	 * @return
	 */
	public UserOauth getUserOauthBySrc(String srcid,String openId);
	
	/**
	 * 根据公众号appid与openId获取授权信息
	 * @param appid
	 * @param openId
	 * @return
	 */
	public UserOauth getUserOauth(String appid,String openId);

	int addMpWeixinToken(String appid, String token, int expires, int type);

	Map<Integer, WebWeixinToken> getWebTokenByAppid(String appid);
	
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
