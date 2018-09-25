package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.dao;

import java.util.List;

import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserFortune;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserIncome;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserOptRecord;

public interface EventTwistedEggMachineDao {

	/**
	 * 获取用户任务扭蛋数量
	 * @param uid
	 * @return
	 */
	int getTeggNum(Long uid);

	/**
	 * 获取最近100条扭蛋抽奖记录
	 * @param num
	 * @param uid 
	 * @return
	 */
	List<EventTwistedEggMachineUserOptRecord> getRecentlyUserOptRecords(int num, Long uid);

	/**
	 * 获取手气值前几位的用户手气值信息
	 * @param num
	 * @param dateCode 
	 * @return
	 */
	List<EventTwistedEggMachineUserFortune> getPreviousUserFortune(int num, int dateCode);

	/**
	 * 获取用户当日手气值信息
	 * @param uid
	 * @param dateCode 
	 * @return
	 */
	EventTwistedEggMachineUserFortune getUserFortuneByUid(Long uid, int dateCode);

	/**
	 * 修改用户任务扭蛋数量
	 * @param uid
	 * @param num
	 * @return
	 */
	int updateUserTwistedEggNum(Long uid, int num);

	/**
	 * 修改用户任务扭蛋数量
	 * @param uid
	 * @param num
	 * @return
	 */
	int saveOrUpdateUserTwistedEggNum(Long uid, int num);

	/**
	 * 保存用户任务扭蛋记录
	 * @param uid
	 * @param dateCode
	 * @param num
	 * @param remark
	 */
	int saveUserTwistedEggRecord(Long uid, int dateCode, int num, String remark);

	/**
	 * 保存抽奖记录
	 * @param uid
	 * @param type
	 * @param awardType
	 * @param awardId
	 * @param awardName
	 * @param awardCoin
	 * @param remark
	 * @param dateCode 
	 * @param isRobot 
	 * @return
	 */
	int saveUserOptRecord(Long uid, int currTimes, int type, int awardType, int awardId, String awardName, int awardCoin,
			String remark, int dateCode, int isRobot);

	/**
	 * 获取用户抽奖次数
	 * @param uid
	 * @param type
	 * @return
	 */
	int getCurrTimes(Long uid, int type);

	/**
	 * 获取用户当天抽奖的奖品价值
	 * @param uid
	 * @param dateCode
	 * @param type 
	 * @return
	 */
	int getCurrCoinAwardSum(Long uid, int dateCode, int type);

	/**
	 * 保存or更新用户手气值
	 * @param uid
	 * @param value
	 * @param dateCode 
	 * @return
	 */
	int saveOrUpdateUserFortune(Long uid, int value, int dateCode);

	/**
	 * 保存用户手气记录
	 * @param uid
	 * @param dateCode
	 * @param value
	 * @param remark
	 * @return
	 */
	int saveUserFortuneRecord(Long uid, int dateCode, int value, String remark);

	/**
	 * 获取用户收益
	 * @param uid
	 * @return
	 */
	EventTwistedEggMachineUserIncome getUserIncome(long uid);

	/**
	 * 保存or更新用户收益
	 * @param uid
	 * @param value
	 * @return
	 */
	int saveOrUpdateUserIncome(Long uid, int value);

	/**
	 * 保存用户收益记录
	 * @param uid
	 * @param value
	 * @param dateCode
	 * @param remark
	 * @return
	 */
	int saveUserIncomeRecord(Long uid, int value, int dateCode, String remark);

	/**
	 * 获取累计奖品价值
	 * @param type
	 * @return
	 */
	int getCoinAwardSum(int type);


}
