package com.imlianai.zjdoll.app.modules.support.ranking.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollRankReward;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;

public interface RankingDao {

	
	/**
	 * 增加用户周充值汇总
	 * @param cost
	 * @param uid
	 * @param weekCode
	 * @return
	 */
	public int addChargeSum(int cost, long uid, String weekCode);
	
	/**
	 * 获取总充值金额
	 * @param uid
	 * @param weekCode
	 * @return
	 */
	public int getChargeSum(long uid, String weekCode);

	/**
	 * 增加才气值
	 * @param cost
	 * @param uid
	 * @param weekCode
	 * @return
	 */
	public int addRichValue(double cost, long uid, String weekCode);
	
	/**
	 * 获取才气值
	 * @param uid
	 * @param weekCode
	 * @return
	 */
	public RankingItem getRichValue(long uid, String weekCode);

	/**
	 * 获取才气榜
	 * @param size
	 * @return
	 */
	public List<RankingItem> getRichRank( String weekCode,int size);
	
	/**
	 * 按类型获取奖励
	 * @param type
	 * @return
	 */
	public List<DollRankReward> getRewardListByType(int type);
}
