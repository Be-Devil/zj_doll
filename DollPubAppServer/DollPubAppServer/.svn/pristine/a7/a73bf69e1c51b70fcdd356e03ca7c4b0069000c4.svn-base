package com.imlianai.dollpub.app.modules.core.doll.pattern.dao;

import com.imlianai.dollpub.domain.doll.pattern.BusPatternStat;

public interface DollBusPatternDao {

	/**
	 * 获取当前娃娃机抓力统计
	 * 
	 * @param busId
	 * @return
	 */
	public BusPatternStat getStat(int busId);

	/**
	 * 初始化
	 * 
	 * @param busId
	 */
	public void initStat(int busId);

	/**
	 * 
	 * @param optId
	 * @param logId
	 * @param uid
	 * @param busId
	 * @param deviceId
	 * @param current
	 * @param strong
	 */
	public void addStrongRecord(long optId, long logId, long uid, int busId,
			String deviceId, int round, int current, int strong);
	
	/**
	 * 更新统计
	 * @param busId
	 * @param round
	 */
	public void updateStat(int busId,int round);
	
	/**
	 * 重置第几轮
	 * @param busId
	 */
	public void resetStatRound(int busId);
}
