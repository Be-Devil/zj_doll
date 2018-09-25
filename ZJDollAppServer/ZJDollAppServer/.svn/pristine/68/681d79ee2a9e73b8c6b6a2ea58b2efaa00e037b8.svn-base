package com.imlianai.zjdoll.app.modules.core.doll.pattern.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.pattern.BusPatternStat;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.DollBusStrongDetailRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserSpecialCatchRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserStageRecord;

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
	 * 
	 * @param busId
	 * @param round
	 */
	public void updateStat(int busId, int round);

	/**
	 * 重置第几轮
	 * 
	 * @param busId
	 */
	public void resetStatRound(int busId);

	/**
	 * 更新强爪标识
	 * 
	 * @param busId
	 * @param strong
	 */
	public void updateStrongFlag(int busId, int strong);

	/**
	 * 捉取详情
	 * 
	 * @param optId
	 * @param busId
	 * @param uid
	 * @param totalStrong
	 * @param success
	 */
	public void addDetailRecord(long optId, int busId, long uid,
			int busSettingStrong, int totalStrong, int currentCount, int success);

	/**
	 * 成功设置完成
	 * 
	 * @param optId
	 */
	public void updateDetailRecord(long optId);

	/**
	 * 查询娃娃机记录流失
	 * 
	 * @param busId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DollBusStrongDetailRecord> getDollBusStrongDetailRecord(
			int busId, String start, String end);

	/**
	 * 初始化强爪记录
	 * 
	 * @param uid
	 */
	public void initUserStrongRecord(long uid, int successExpect);

	public void updateStrongCount(long uid, long optId);

	/**
	 * 是否有剩余强爪次数
	 * 
	 * @param uid
	 * @return
	 */
	public int hasStrongChange(long uid);

	/**
	 * 获取增景概率统计
	 * 
	 * @param busId
	 * @return
	 */
	BusPatternStat getStatZengjing(int busId);

	/**
	 * 初始化增景概率统计
	 * 
	 * @param busId
	 */
	void initStatZengjing(int busId);

	/**
	 * 更新增景统计
	 * 
	 * @param busId
	 * @param round
	 */
	void updateStatZengjing(int busId, int round);

	/**
	 * 重置round数
	 * 
	 * @param busId
	 */
	void resetStatRoundZengjing(int busId);

	void updateStrongFlagZengjing(int busId, int strong);

	/**
	 * 消耗强爪机会
	 * 
	 * @param uid
	 * @param change
	 * @return
	 */
	int comsumeStrongChange(long uid, int change);

	/**
	 * 获取用户特殊捉取状态
	 * 
	 * @param uid
	 * @return
	 */
	UserSpecialCatchRecord getUserSpecialCatchRecord(long uid);

	/**
	 * 更新累计捉取次数
	 * 
	 * @param uid
	 */
	void updateCatchCount(long uid);

	public int getCatchUserSetting(long uid);

	/**
	 * 获取用户捉取策略
	 * 
	 * @param uid
	 * @return
	 */
	public UserStageRecord getUserStage(long uid);

	/**
	 * 初始化用户策略
	 * 
	 * @param uid
	 */
	public void initUserStage(long uid);

	/**
	 * 更新用户本次抓力记录
	 * 
	 * @param uid
	 * @param optId
	 * @param isStrong
	 */
	public void updateUserStage(long uid, long optId, boolean isStrong);
	
	/**
	 * 更新用户策略阶段
	 * @param uid
	 * @param stage
	 */
	public void updateUserStage(long uid,int stage);
	
	/**
	 * 更新用户成功次数
	 * @param uid
	 * @param isStrong
	 */
	public void updateUserStageSuccess(long uid,boolean isStrong);
}
