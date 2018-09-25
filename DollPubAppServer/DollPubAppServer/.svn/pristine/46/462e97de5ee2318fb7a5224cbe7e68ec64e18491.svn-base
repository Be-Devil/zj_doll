package com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface EventNewyearRecharge20180207Service {

	/**
	 * 处理新春充值活动
	 * @param cost
	 * @param uid
	 */
	void handleRecharge(double cost, long uid);

	/**
	 * 获取新春充值活动状态信息
	 * @return
	 */
	BaseRespVO getStatus();

	/**
	 * 获取新春充值活动榜单
	 * @param uid
	 * @return
	 */
	BaseRespVO getRanking(Long uid);

	/**
	 * 给用户添加福气值
	 * @param uid
	 * @param value
	 * @return
	 */
	BaseRespVO addValue(Long uid, double value);

	/**
	 * 12点推送榜单排名通知
	 */
	void newYearEventPushRankingMsg();

	/**
	 * 活动结束推送通知
	 */
	void newYearEventEndPushMsg();
	
	/**
	 * 定时消息
	 */
	void handleMsgTask();
}
