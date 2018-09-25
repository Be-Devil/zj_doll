package com.imlianai.dollpub.app.modules.publics.oauth.wechat.event.dao;

public interface WechatEventDao {

	/**
	 * 是否已获取
	 * @param openId
	 * @param unionId
	 * @return
	 */
	public int hasGetReward(String unionId);
	
	/**
	 * 是否已领取
	 * @param uid
	 * @return
	 */
	public int hasGetReward(long uid);
	
	/**
	 * 增加领取奖励记录
	 * @param uid
	 * @param openId
	 * @param unionId
	 * @return
	 */
	public int addGetReward(long uid,String openId,String unionId,int amt);
	
	/**
	 * 是否已领取签到奖励
	 * @param uid
	 * @return
	 */
	public int hasGetSignReward(long uid);

	/**
	 * 领取登录奖励
	 * @param uid
	 * @param reward
	 * @return
	 */
	public int addGetSignReward(long uid, int reward);
}
