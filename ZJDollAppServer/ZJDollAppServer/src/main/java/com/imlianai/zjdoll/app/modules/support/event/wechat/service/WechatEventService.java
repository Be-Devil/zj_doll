package com.imlianai.zjdoll.app.modules.support.event.wechat.service;

public interface WechatEventService {

	/**
	 * 获取关注信息
	 * @param unionId
	 * @param toUserID
	 * @return
	 */
	public String getFollowMsg(String unionId,
			String toUserID);
	
	/**
	 * 获取消息奖励
	 * @param unionId
	 * @param openId
	 * @return
	 */
	public String getMsgReward(String unionId,String openId);

	/**
	 * 获取邀请信息
	 * @param unionId
	 * @param openId
	 * @return
	 */
	public String getInviteMsg(String unionId, String openId);
}
