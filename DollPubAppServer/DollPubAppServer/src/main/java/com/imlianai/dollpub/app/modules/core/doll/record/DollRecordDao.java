package com.imlianai.dollpub.app.modules.core.doll.record;

import java.util.List;

import com.imlianai.dollpub.domain.doll.DollAppealRecord;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.DollSuccessOptRecord;
import com.imlianai.dollpub.domain.doll.WatchRecord;

public interface DollRecordDao {

	/**
	 * 保存娃娃机操作记录
	 * 
	 * @param record
	 * @return
	 */
	int saveDollOptRecord(DollOptRecord record);

	/**
	 * 获取操作记录
	 * 
	 * @param lastId
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	List<DollOptRecord> getOptRecords(long lastId, Long uid, int pageSize);

	/**
	 * 获取操作记录
	 * 
	 * @param optId
	 * @return
	 */
	DollOptRecord getOptRecord(long optId);

	/**
	 * 保存申诉记录
	 * 
	 * @param optId
	 * @param uid
	 * @param reason
	 * @return
	 */
	int saveDollAppealRecord(int customerId,int groupId,long optId, Long uid, String reason);

    /**
     * 保持申诉记录
     * @param dollAppealRecord
     * @return
     */
	int saveDollAppealRecord(DollAppealRecord dollAppealRecord);

	/**
	 * 获取最近抓到娃娃的操作记录
	 * 
	 * @param num
	 *            娃娃数量
	 * @return
	 */
	List<DollSuccessOptRecord> getSuccOptRecord(int num);

	/**
	 * 增加观看记录
	 * 
	 * @param uid
	 * @param busId
	 * @return
	 */
	public int addWatchRecord(long uid, int busId);

	/**
	 * 记录上机日志
	 * 
	 * @param uid
	 * @param busId
	 * @param deviceId
	 * @param res
	 */
	public void addApplyLog(long uid, int busId, String deviceId, String res);

	/**
	 * 修改娃娃机操作记录
	 * 
	 * @param record
	 * @return
	 */
	int updateDollOptRecord(DollOptRecord record);

	/**
	 * 获取申诉记录
	 * 
	 * @param optId
	 * @return
	 */
	DollAppealRecord getAppealRecord(long optId);

	/**
	 * 保存娃娃机操作成功记录
	 * 
	 * @param optId
	 * @param uid
	 * @param dollId
	 * @return
	 */
	int saveDollSucOptRecord(long optId, long uid, int dollId, int busId,int groupId,int customerId,long agentId);

	/**
	 * 根据上机编号获取操作记录
	 * 
	 * @param optId
	 * @return
	 */
	public DollOptRecord getOptRecordByLogId(long logId);

	/**
	 * 获取娃娃机最近捉中记录
	 * 
	 * @param busId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DollSuccessOptRecord> getBusRewardRecords(int busId, int page,
			int pageSize);

	/**
	 * 获取观众列表
	 * 
	 * @param busId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<WatchRecord> getWatchList(int busId, int page, int pageSize);
	
	/**
	 * 获取我所在的娃娃机
	 * @param uid
	 * @return
	 */
	public int getWatchBus(long uid);


	public int updateWatchBus(long uid,int typeAuth);

	
	/**
	 * 删除观看记录
	 * @param uid
	 * @param busId
	 */
	public void deleteWatchRecord(long uid, int busId);
	
	/**
	 * 记录用户每天捉取次数
	 * @param uid
	 * @param isSuccess
	 */
	public void addDailyPlayRecord(long uid,int isSuccess);
	
	/**
	 * 获取用户当天捉取次数
	 * @param uid
	 * @return
	 */
	public int getDailyPlayCount(long uid);
	
	/**
	 * 获取最近抓到娃娃的操作记录
	 * 
	 * @param num
	 *            娃娃数量
	 * @return
	 */
	List<DollSuccessOptRecord> getSuccOptRecord(int customerId,int num);
	
}
