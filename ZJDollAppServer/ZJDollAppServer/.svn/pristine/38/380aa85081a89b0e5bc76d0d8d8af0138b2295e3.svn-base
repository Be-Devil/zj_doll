package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.service;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.PlayReqVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.UserAwardInfo;

public interface EventTwistedEggMachineService {

	/**
	 * 获取扭蛋机信息
	 * @param uid
	 * @return
	 */
	BaseRespVO getInfo(Long uid);

	/**
	 * 获取扭蛋记录
	 * @param uid
	 * @param limit
	 * @return
	 */
	List<UserAwardInfo> getTwistedEggRecords(Long uid, int limit);

	/**
	 * 抽奖
	 * @param reqVO
	 * @return
	 */
	BaseRespVO play(PlayReqVO reqVO);

	/**
	 * 修改用户任务扭蛋数量
	 * @param uid
	 * @param dateCode
	 * @param num
	 * @param remark
	 */
	void updateUserTwistedeggNum(Long uid, int dateCode, int num, String remark);

	/**
	 * 保存用户抽奖记录
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
	int saveUserOptRecord(Long uid, int type, int awardType, int awardId, String awardName, int awardCoin, String remark, int dateCode, int isRobot);

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
	 * @param dateCode
	 * @param value
	 * @param remark
	 */
	void saveUserFortune(Long uid, int dateCode, int value, String remark);

	/**
	 * 保存用户任务扭蛋使用记录
	 * @param uid
	 * @param dateCode
	 * @param num
	 * @param remark
	 */
	void saveUserwistedEgg(Long uid, int dateCode, int num, String remark);

	/**
	 * 每15分钟消息推送
	 */
	void twistedEggMachineEvery15MinsMsg();

	/**
	 * 手气王榜单消息推送
	 */
	void twistedEggMachineRankingMsg();


}
