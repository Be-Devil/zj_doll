package com.imlianai.dollpub.app.modules.core.api.dao;

import java.util.Date;
import java.util.List;

import com.imlianai.dollpub.domain.doll.DollBusStatus;

public interface DollBusStatusDAO {
	/**
	 * 添加机器状态
	 * @param dollBusStatus
	 * @return
	 */
	public int addDollBusStatus(DollBusStatus dollBusStatus);

	/**
	 * 添加机器状态
	 * @param busId
	 * @param optId
	 * @param uid
	 * @param status
	 * @param time
	 * @return
	 */
	public int addDollBusStatus(int busId,long optId,long uid,int status,int time);

	/**
	 * 更新机器状态
	 * @param busId
	 * @param status
	 * @return
	 */
	public int updateDollBusStatus(int busId,int status);

	/**
	 * 通过busId获取对应机器的状态
	 * @param busId
	 * @return
	 */
	public DollBusStatus getDollBusStatusByBusId(int busId);

	/**
	 * 通过busId删除机器状态
	 * @param busId
	 * @return
	 */
	public int deleteDollBusStatusByBusId(int busId);

	/**
	 * 获取超时的记录
	 * @param condition  0:maxPlayTime,1:keepTime
	 * @return
	 */
	public List<DollBusStatus> getInvaildRecord(int condition);

	/**
	 * 更新持续时间
	 * @param busId
	 * @param keepTime
	 * @return
	 */
	public int updateDollBusStatusKeepTime(int busId,Date keepTime);

	/**
	 * 更新下爪标记
	 * @param busId
	 * @param optId
	 * @return
	 */
	public int updateDownClaw(int busId, long optId);
	
}
