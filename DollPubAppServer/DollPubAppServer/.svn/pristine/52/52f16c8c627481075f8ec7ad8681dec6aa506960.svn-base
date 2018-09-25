package com.imlianai.dollpub.app.modules.core.doll.record;

import java.util.List;

import com.imlianai.dollpub.domain.doll.DollAppealRecord;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.DollRewardRecord;
import com.imlianai.dollpub.domain.user.UserCommon;

/**
 * 夹娃娃记录
 * 
 * @author tensloveq
 * 
 */
public interface DollRecordService {

	/**
	 * 保存娃娃机操作记录
	 * 
	 * @param record
	 * @return
	 */
	public int saveDollOptRecord(DollOptRecord record);

	/**
	 * 获取操作记录列表
	 * 
	 * @param lastId
	 * @param uid
	 * @param pageSize
	 * @return
	 */
	public List<DollOptRecord> getOptRecords(long lastId, Long uid, int pageSize);

	/**
	 * 获取操作记录
	 * 
	 * @param optId
	 * @return
	 */
	public DollOptRecord getOptRecord(long optId);

	/**
	 * 根据上机编号获取操作记录
	 * 
	 * @param optId
	 * @return
	 */
	public DollOptRecord getOptRecordByLogId(long logId);

	/**
	 * 保存申诉记录
	 * 
	 * @param optId
	 * @param uid
	 * @param reason
	 * @return
	 */
	public int saveDollAppealRecord(int customerId,int groupId,long optId, Long uid, String reason);


	public int saveDollAppealRecord(DollAppealRecord dollAppealRecord);



	/**
	 * 获取最近抓到的娃娃列表
	 * 
	 * @param num
	 *            娃娃数量
	 * @return
	 */
	public List<DollRewardRecord> getRecentlyDollList(int num);
	
	/**
	 * 根据customerId获取最近抓取记录
	 * @param customerId
	 * @param num
	 * @return
	 */
	public List<DollRewardRecord> getRecentlyDollList(int customerId,int num);

	/**
	 * 获取娃娃机最近捉中记录
	 * 
	 * @param busId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DollRewardRecord> getBusRewardRecords(int busId, int page,
			int pageSize);

	/**
	 * 增加观看记录
	 * 
	 * @param uid
	 * @param busId
	 * @return
	 */
	public int addWatchRecord(long uid, int busId);
	
	/**
	 * 获取我所在的娃娃机
	 * @param uid
	 * @return
	 */
	public int getWatchBus(long uid);
	
	/**
	 * 删除观看记录
	 * @param uid
	 * @param busId
	 */
	public void deleteWatchRecord(long uid, int busId);

	/**
	 * 获取申诉记录
	 * 
	 * @param optId
	 * @return
	 */
	public DollAppealRecord getAppealRecord(long optId);

	/**
	 * 获取观众列表
	 * @param busId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<UserCommon> getWatchList(int busId,int page,int pageSize);
	
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
}
