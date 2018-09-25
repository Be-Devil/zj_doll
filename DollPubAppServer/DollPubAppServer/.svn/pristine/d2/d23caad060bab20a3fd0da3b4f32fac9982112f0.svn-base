package com.imlianai.dollpub.app.modules.publics.oauth.wechat.event.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WeiXinArticles;
import com.imlianai.dollpub.domain.user.UserBase;

public interface WechatEventService {

	/**
	 * 获取每日签到信息
	 * @param unionId
	 * @param toUserID
	 * @return
	 */
	public String getSignReward(String publicAccountID,String openId);
	
	/**
	 * 获取关注消息奖励
	 * @param unionId
	 * @param openId
	 * @return
	 */
	public String getFollowMsg(String publicAccountID,String openId);

	/**
	 * 获取邀请信息
	 * @param unionId
	 * @param openId
	 * @return
	 */
	public String getInviteMsg(String publicAccountID, String openId);

	/**
	 * 五元红包
	 * @return
	 */
	public List<WeiXinArticles> get5Redpacket();

	/**
	 * 是否已领取奖励
	 * @param userBase
	 * @return
	 */
	public int hasReward(UserBase userBase);

	/**
	 * 领取奖励
	 * @param userBase
	 * @return
	 */
	public int getReward(UserBase userBase);
}
