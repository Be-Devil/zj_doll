package com.imlianai.zjdoll.app.modules.core.doll.record;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollAppealRecord;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.DollRewardRecord;
import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.zjdoll.app.modules.core.doll.vo.UserAbandonSummry;

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
	 * 修改娃娃机操作记录
	 * 
	 * @param record
	 * @return
	 */
	public int updateDollOptRecord(DollOptRecord record);

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
	 * @param deviceCompany 
	 * 
	 * @param optId
	 * @return
	 */
	public DollOptRecord getOptRecordByLogId(long logId, int deviceCompany);

	/**
	 * 保存申诉记录
	 * 
	 * @param optId
	 * @param uid
	 * @param reason
	 * @return
	 */
	public int saveDollAppealRecord(long optId, Long uid, String reason);

	/**
	 * 获取最近抓到的娃娃列表
	 * 
	 * @param num
	 *            娃娃数量
	 * @return
	 */
	public List<DollRewardRecord> getRecentlyDollList(int num);

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
	public int deleteWatchRecord(long uid, int busId);

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
	 * 增加用户连续上机次数
	 * @param uid
	 * @param isInit
	 */
	public void addUserContinueTime(long uid,boolean isInit);
	
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
	 * 处理用户抓取成功异常状态
	 * @param uid
	 * @param busId
	 * @param optId
	 */
	public void handleUserSuccessError(long uid,int busId,long optId);
	
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
	 * 更新上次累计数量
	 * @param uid
	 * @param lastTotalNum
	 */
	public void updateUserAbandonSummry(long uid,int lastTotalNum);
	
	/**
	 * 记录用户放弃上机
	 * @param uid
	 * @param optId
	 */
	public void addUserAbandonSummry(long uid, long optId);
	
	/**
	 * 获取用户连续上机次数
	 * @param uid
	 * @return
	 */
	public int getUserContinueTime(long uid);
	
	/**
	 * 更新标记操作已处理
	 * @param logId
	 * @param deviceCompany
	 * @return
	 */
	public int updateOptHasHandle(long optId);
	
	/**
	 * 获取特殊的娃娃状态
	 * @param dollId
	 */
	public int getSpecDollStatus(int dollId);
	
	/**
	 * 更新强爪标识
	 * @param optId
	 * @param isStrong
	 */
	public void updateOptRecordStrong(long optId,int isStrong);
}
