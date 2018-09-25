package com.imlianai.zjdoll.app.modules.support.ranking.service;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.user.RankingItem;

public interface RankingService {

	
	/**
	 * 处理充值榜单统计
	 * @param cost
	 * @param uid
	 */
	public void handleRecharge(double cost, long uid);

	/**
	 * 获取充值榜
	 * @param size
	 * @return
	 */
	public List<RankingItem> getChargeRanks(int size);
	
	/**
	 * 获取我的充值排名
	 * @param uid
	 * @return
	 */
	public RankingItem getMyChargeRank(long uid);

	/**
	 * 按类型获取奖励
	 * @param list
	 * @param type
	 * @return
	 */
	public List<RankingItem> getRankListWithReward(List<RankingItem> list, int type);

	/**
	 * 增加充值榜单值
	 * @param cost
	 * @param uid
	 * @param weekCode
	 */
	public void addChargeSum(double cost, long uid, String weekCode);
	
	/**
	 * 增加才气值
	 * @param cost
	 * @param uid
	 * @param weekCode
	 * @return
	 */
	public int addRichValue(double cost, long uid, String weekCode);
}
