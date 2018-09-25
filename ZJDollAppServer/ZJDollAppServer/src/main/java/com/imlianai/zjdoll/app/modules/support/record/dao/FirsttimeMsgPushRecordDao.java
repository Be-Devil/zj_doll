package com.imlianai.zjdoll.app.modules.support.record.dao;

import com.imlianai.zjdoll.domain.record.FirsttimeMsgPushRecord;

public interface FirsttimeMsgPushRecordDao {

	/**
	 * 获取首次推送记录
	 * @param uid
	 * @param type
	 * @param num
	 * @return
	 */
	FirsttimeMsgPushRecord getFirsttimeMsgPushRecord(Long uid, int type, int num);

	/**
	 * 保存首次推送记录
	 * @param uid
	 * @param type
	 * @param num
	 * @return
	 */
	int saveFirsttimeMsgPushRecord(Long uid, int type, int num);
}
