package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.service;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo.TwistedEggInfo;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.AwardItem;

public interface EventTwistedEgg20180305Service {

	/**
	 * 扭蛋列表
	 * @param uid
	 * @return
	 */
	List<TwistedEggInfo> getList(Long uid);
	
	/**
	 * 充值处理
	 * @param cost
	 * @param uid
	 */
	void handleRecharge(double cost, long uid);
	/**
	 * 抽奖处理
	 * @param uid
	 * @return 
	 */
	AwardItem play(Long uid);

	/**
	 * 领取任务扭蛋
	 * @param uid
	 * @param teggId
	 * @return
	 */
	BaseRespVO receive(Long uid, int teggId);

	/**
	 * 活动结束后7天改为已过期
	 */
	void twistedEggUpdateStatusTask();

	/**
	 * 获取日充值金额
	 * @param uid
	 * @return
	 */
	int getDayRechargeAmt(Long uid);

	/**
	 * 生成一条机器人抽奖记录
	 */
	void twistedEggEvery5MinsTask();

}
