package com.imlianai.dollpub.app.modules.core.doll.pattern.service;

import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.pattern.BusPatternStat;

/**
 * 捉力模式相关
 * 
 * @author tensloveq
 * 
 */
public interface DollBusPatternService {

	/**
	 * 上机后开始抓力逻辑
	 * @param bus
	 * @param record
	 */
	public void handleStartPlay(DollBus bus, DollOptRecord record);
	
	/**
	 * 获取当前娃娃机抓力统计
	 * @param busId
	 * @return
	 */
	public BusPatternStat getStat(int busId);

	void handleCatchSuccess(DollBus bus, DollOptRecord record);
}
