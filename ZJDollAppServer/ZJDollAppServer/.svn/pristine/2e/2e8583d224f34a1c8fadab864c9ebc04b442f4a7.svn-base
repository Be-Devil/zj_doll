package com.imlianai.zjdoll.app.modules.core.doll.record;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollAppealRecord;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.DollSuccessOptRecord;
import com.imlianai.zjdoll.domain.doll.WatchRecord;
import com.imlianai.zjdoll.app.modules.core.doll.vo.UserAbandonSummry;

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
	int saveDollAppealRecord(long optId, Long uid, String reason);

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
	public void addApplyLog(long uid, int busId,int company,  String deviceId, String res);

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
	int saveDollSucOptRecord(long optId, long uid, int dollId, int busId);

	/**
	 * 根据上机编号获取操作记录
	 * @param deviceCompany 
	 * 
	 * @param optId
	 * @return
	 */
	public DollOptRecord getOptRecordByLogId(long logId, int deviceCompany);
	
	/**
	 * 更新标记操作已处理
	 * @param logId
	 * @param deviceCompany
	 * @return
	 */
	public int updateOptHasHandle(long optId);

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
	
	/**
	 * 删除观看记录
	 * @param uid
	 * @param busId
	 */
	public int deleteWatchRecord(long uid, int busId);
	
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
	 * 更新录播地址
	 * @param optId
	 * @param url1
	 * @param url2
	 * @param state
	 * @return
	 */
	public int updateVideoRecord(long optId,String url1,String url2,int state);

	/**
	 * 记录用户游戏汇总
	 * @param uid
	 * @param optId
	 * @param isSuccess
	 */
	public void addUserPlaySummry(long uid, long optId, int isSuccess);

	/**
	 * 记录用户放弃上机
	 * @param uid
	 * @param optId
	 */
	public void addUserAbandonSummry(long uid, long optId);
	
	/**
	 * 获取用户累计操作次数
	 * @param uid
	 * @return
	 */
	public int getUserTotalPlayCount(long uid);
	
	/**
	 * 获取用户放弃上机汇总
	 * @param uid
	 * @return
	 */
	public UserAbandonSummry getUserAbandonSummry(long uid);

	/**
	 * 保存娃娃消耗记录
	 * @param dollId
	 * @return
	 */
	int saveDollConsumeRecord(int dollId);
	
	/**
	 * 增加用户连续上机次数
	 * @param uid
	 * @param isInit
	 */
	public void addUserContinueTime(long uid,boolean isInit);
	
	/**
	 * 获取用户连续上机次数
	 * @param uid
	 * @return
	 */
	public int getUserContinueTime(long uid);
	
	/**
	 * 更新上次累计数量
	 * @param uid
	 * @param lastTotalNum
	 */
	public void updateUserAbandonSummry(long uid,int lastTotalNum);
	
	/**
	 * 增加并获取抓取成功记录
	 * @param busId
	 * @return
	 */
	public int addAndGetSuccess(int busId,int minute);
	
	/**
	 * 更新强爪标识
	 * @param optId
	 * @param isStrong
	 */
	public void updateOptRecordStrong(long optId,int isStrong);
}
