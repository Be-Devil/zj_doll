package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.dao;

public interface OauthWeixinDao {

	/**
	 * 插入开发者平台绑定信息
	 * @param openId
	 * @param uid
	 * @param unionId
	 * @return
	 */
	public int addOauthBondInfo(String openId,long uid,String unionId);
	
	/**
	 * 判断uid是否有绑定记录
	 * @param uid
	 * @return
	 */
	public int hasBondInfo(long uid);
	
	/**
	 * 根据unionId获取对应uid
	 * @param unionId
	 * @return
	 */
	public Long getBondUidByUnionId(String unionId);
}
