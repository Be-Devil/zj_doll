package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.dao;

import java.util.List;

import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserDayRechargeSum;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserRechargeSum;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserTwistedeggInfo;

public interface EventTwistedEgg20180305Dao {

	/**
	 * 获取用户扭蛋信息列表
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	List<Event20180306TwistedeggUserTwistedeggInfo> getTwistedeggInfoList(Long uid, int dateCode);

	/**
	 * 保存or更新用户充值信息
	 * @param uid
	 * @param dateCode
	 * @param cost
	 * @return
	 */
	int saveOrUpdateUserRechargeSum(long uid, int type, double cost);

	/**
	 * 获取用户日充值信息
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	Event20180306TwistedeggUserDayRechargeSum getUserDayRechargeSum(long uid, int dateCode);

	/**
	 * 保存or更新用户扭蛋信息
	 * @param uid
	 * @param dateCode
	 * @param i
	 * @return
	 */
	int saveOrUpdateUserTwistedeggInfo(long uid, int dateCode, int tEggId);

	/**
	 * 获取用户活动充值信息
	 * @param uid
	 * @param type
	 * @return
	 */
	Event20180306TwistedeggUserRechargeSum getUserRechargeSum(Long uid, int type);

	/**
	 * 获取用户扭蛋信息
	 * @param uid
	 * @param teggId
	 * @param dateCode
	 * @return
	 */
	Event20180306TwistedeggUserTwistedeggInfo getUserTwistedeggInfoByParams(Long uid, int teggId, int dateCode);

	/**
	 * 修改用户扭蛋状态
	 * @param uid
	 * @param teggId
	 * @param dateCode
	 * @return
	 */
	int updateUserTwistedEggInfoStatus(Long uid, int teggId, int dateCode);

	/**
	 * 保存or更新用户日充值信息
	 * @param uid
	 * @param dateCode
	 * @param cost
	 * @return
	 */
	int saveOrUpdateUserDayRechargeSum(long uid, int dateCode, double cost);

	/**
	 * 保存用户活动充值记录
	 * @param uid
	 * @param type
	 * @param dateCode
	 * @param amount
	 * @param remark
	 * @return
	 */
	int saveUserRechargeRecord(Long uid, int type, int dateCode, double amount, String remark);

	/**
	 * 过期处理
	 */
	int handleExpire();

}
