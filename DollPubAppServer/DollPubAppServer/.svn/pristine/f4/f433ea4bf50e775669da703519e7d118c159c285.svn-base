package com.imlianai.dollpub.app.modules.core.api.service;

import java.util.Date;
import java.util.List;

import com.imlianai.dollpub.domain.doll.DollBusStatus;

public interface DollBusStatusService {
	public DollBusStatus getDollBusStatusByBusId(int busId);
	
	/**
	 * 获取超时的记录
	 * @return
	 */
	public List<DollBusStatus> getInvaildRecord(int condition);
	
	/**
	 * 删除上机状态
	 * @param busId
	 * @return
	 */
	public int deleteDollBusStatusByBusId(int busId);
	
	/**
	 * 更新持续时间
	 * @param busId
	 * @param keepTime
	 * @return
	 */
	public int updateKeepTime(int busId,Date keepTime);
	
	/**
	 * 判断用户是否可上机
	 * @param busId
	 * @param uid
	 * @return
	 */
	public boolean isApply(int busId,long uid);
	
}
