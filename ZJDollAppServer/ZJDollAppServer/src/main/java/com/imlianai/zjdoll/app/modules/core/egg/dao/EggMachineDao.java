package com.imlianai.zjdoll.app.modules.core.egg.dao;



import com.imlianai.zjdoll.domain.egg.*;

import java.util.List;

public interface EggMachineDao {

	/**
	 * 获取扭蛋机分类列表
	 * @return
	 */
	List<EggMacClassifyInfo> getEggMacClassifies();

	/**
	 * 获取扭蛋机列表
	 * @return
	 */
	List<EggMachine> getEggMachines();

	/**
	 * 获取分类下的扭蛋机ID
	 * @param type
	 * @return
	 */
	List<Long> getMachineIdsByType(int type);

	/**
	 * 获取扭蛋机对应的奖品列表
	 * @param machineId
	 * @return
	 */
	List<EggMachineRewardConf> getRewardConfsByMachineId(long machineId);

	/**
	 * 获取已发放的奖品数量
	 * @param dollId
	 * @return
	 */
	int getAwardNum(int dollId);

	/**
	 * 获取某台扭蛋机最近的用户抽奖记录列表
	 * @param size
	 * @param machineId 
	 * @return
	 */
	List<EggMachineUserPlayRecord> getRecentlyUserPlayRecords(int size, long machineId);

	/**
	 * 获取用户账户信息
	 * @param uid
	 * @return
	 */
	EggMachineUserAccount getUserAccount(Long uid);

	/**
	 * 保存or更新用户时光劵
	 * @param uid
	 * @param num
	 * @return
	 */
	int updateUserTimeCoupon(Long uid, int num);

	/**
	 * 修改用户余额
	 * @param uid
	 * @param amount
	 * @return
	 */
	int updateUserBalance(Long uid, int amount);

	/**
	 * 修改扭蛋机奖品发放数量
	 * @param dollId
	 * @param num
	 * @return
	 */
	int updateAwardNum(int dollId, int num);

	/**
	 * 保存用户上机记录
	 * @param uid
	 * @param dateCode
	 * @param type
	 * @param machineId
	 * @param remark
	 * @param time 
	 * @return
	 */
	int saveUserOptRecord(Long uid, int dateCode, int type, Long machineId, String remark, int time);

	/**
	 * 保存用户抽奖记录
	 * @param optId
	 * @param uid
	 * @param dateCode
	 * @param dollId
	 * @param machineId 
	 * @return
	 */
	int saveUserPlayRecord(long optId, Long uid, int dateCode, int dollId, Long machineId);

	/**
	 * 获取用户上机记录
	 * @param optId
	 * @return
	 */
	EggMachineUserOptRecord getUserOptRecordByOptId(Long optId);

	/**
	 * 获取超过时间的记录
	 * @param seconds
	 * @return
	 */
	List<EggMachineUserOptRecord> getOvertimeUserOptRecords(int seconds);

	/**
	 * 修改用户操作记录状态
	 * @param id
	 * @param originStatus
	 * @param endStatus
	 * @return
	 */
	int updateUserOptRecord(Long id, int originStatus, int endStatus);

	/**
	 * 获取用户抽奖记录
	 * @param optId
	 * @return
	 */
	List<EggMachineUserPlayRecord> getUserPlayRecordsByOptId(Long optId);

	/**
	 * 修改用户抽奖记录状态
	 * @param id
	 * @param originStatus
	 * @param endStatus
	 * @return
	 */
	int updateUserPlayRecord(Long id, int originStatus, int endStatus);

	/**
	 * 修改扭蛋机状态
	 * @param machineId
	 * @param status
	 * @return
	 */
	int updateEggMachineStatus(Long machineId, int status);

	/**
	 * 修改用户抽奖记录的用户娃娃ID
	 * @param uDollId
	 * @param id
	 * @return
	 */
	int updateUserPlayRecord(long uDollId, Long id);

}
