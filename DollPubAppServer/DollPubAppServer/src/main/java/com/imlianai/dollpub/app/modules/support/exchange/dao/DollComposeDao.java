package com.imlianai.dollpub.app.modules.support.exchange.dao;

import com.imlianai.dollpub.domain.doll.DollRecycleRecord;

public interface DollComposeDao {

	/**
	 * 保存娃娃回收记录
	 * @param uDollId
	 * @param jewel
	 * @return
	 */
	int saveRecycleRecord(long uDollId, int jewel);

	/**
	 * 获取娃娃回收记录
	 * @param uDollId
	 * @return
	 */
	DollRecycleRecord getRecycleRecordByUDollId(Long uDollId);


}
