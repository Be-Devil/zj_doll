package com.imlianai.dollpub.app.modules.support.exchange.dao;

import com.imlianai.dollpub.domain.doll.DollGetRecord;

public interface DollGetDao {

	
	/**
	 * 保存用户申请领取记录
	 * @param record
	 * @return
	 */
	int saveDollGetRecord(DollGetRecord record);

	/**
	 * 获取申请领取记录
	 * @param uDollId
	 * @return
	 */
	DollGetRecord getDollGetRecord(Long uDollId);

}
