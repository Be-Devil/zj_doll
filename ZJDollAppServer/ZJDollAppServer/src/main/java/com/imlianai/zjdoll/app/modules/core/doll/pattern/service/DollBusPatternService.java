package com.imlianai.zjdoll.app.modules.core.doll.pattern.service;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.BusOperatingRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.DollOptRecord.DollOptRecordStrong;
import com.imlianai.zjdoll.domain.doll.pattern.BusPatternStat;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.DollBusStrongDetailRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserStageRecord;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollOperateReqVO;

/**
 * 捉力模式相关
 * 
 * @author tensloveq
 * 
 */
public interface DollBusPatternService {

	/**
	 * 获取当前娃娃机抓力统计
	 * @param busId
	 * @return
	 */
	public BusPatternStat getStat(int busId);

	/**
	 * 处理结束操作
	 * @param bus
	 * @param record
	 */
	public void handleFinalPlay(DollBus bus, DollOptRecord record,int result);
	
	/**
	 * 查询娃娃机记录流失
	 * @param busId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DollBusStrongDetailRecord> getDollBusStrongDetailRecord(int busId,String start,String end);
	
	/**
	 * 处理增景下爪
	 * @param reqVo
	 */
	public int handleZengjingCatch(long optId,DollOperateReqVO reqVo,DollBus dollBus,BusOperatingRecord operatingRecord);
	
	/**
	 * 是否有剩余强爪次数
	 * @param uid
	 * @return
	 */
	public int hasStrongChange(long uid);
	
	/**
	 * 初步判断抓力
	 * @param uid
	 * @param dollBus
	 * @return
	 */
	public DollOptRecordStrong judgeClaw(long uid,DollBus dollBus);

	/**
	 * 机器概率下抓力判断
	 * @param dollBus
	 * @param save 是否保存本次记录
	 * @return
	 */
	public DollOptRecordStrong handleDefaultPattern(DollBus dollBus,boolean save);
	
	/**
	 * 更新用户策略
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
	 * 获取用户捉取策略
	 * 
	 * @param uid
	 * @return
	 */
	public UserStageRecord getUserStage(long uid);
}
