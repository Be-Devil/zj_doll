package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.service;

import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;

public interface WechatService {

	/**
	 * 保存微信用户信息
	 * @param openId
	 * @param unionId
	 * @param name
	 * @param head
	 */
	public void saveWechatUserInfo(String openId,String unionId,String name,String head,String appid);
	
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
}
