package com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.dao;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.domain.Event20180207NewyearRechargeUserBlessing;

public interface EventNewyearRecharge20180207Dao {

	/**
	 * 保存or更新用户充值金额
	 * @param cost
	 * @param uid
	 * @return
	 */
	int saveOrUpdateNewyearrechargeUserRechargeSum(double cost, long uid);

	/**
	 * 获取用户活动期间充值金额
	 * @param uid
	 * @return
	 */
	double getRechargeAmountSum(long uid);

	/**
	 * 保存or更新用户福气值
	 * @param uid
	 * @param value
	 * @return
	 */
	int saveOrUpdateNewyearRechargeUserBlessing(long uid, double value);

	/**
	 * 保存用户福气值记录
	 * @param uid
	 * @param value
	 * @param remark
	 */
	int saveNewyearRechargeUserBlessingRecord(long uid, double value, String remark);

	/**
	 * 获取用户福气值列表
	 * @param num
	 * @return
	 */
	List<Event20180207NewyearRechargeUserBlessing> getUserBlessingList(int num);

	/**
	 * 获取用户福气值
	 * @param uid
	 * @return
	 */
	Event20180207NewyearRechargeUserBlessing getUserBlessingByUid(Long uid);

	/**
	 * 获取排名
	 * @param value
	 * @return
	 */
	int getMyRankCount(double value);

}
