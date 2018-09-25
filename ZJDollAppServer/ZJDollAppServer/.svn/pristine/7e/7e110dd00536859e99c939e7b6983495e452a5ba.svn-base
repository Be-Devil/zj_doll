package com.imlianai.zjdoll.app.modules.support.redpacket.dao;

import java.util.Date;
import java.util.List;

import com.imlianai.zjdoll.domain.redpacket.BusRedpacketRecord;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketLog;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketOpenRecord;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketRewardRecord;

public interface RedpacketDao {

	/**
	 * 保存or更新用户红包金额
	 * @param uid
	 * @param redpackAmt
	 * @return
	 */
	int saveOrUpdateUserRedpacket(long uid, double redpackAmt);

	/**
	 * 保存用户红包日志
	 * @param uid
	 * @param redpackAmt
	 * @param type
	 * @param remark
	 * @param dateCode
	 * @param optId 
	 * @return
	 */
	int saveUserRedpacketLog(long uid, double redpackAmt, int type, String remark, int dateCode, long optId);

	/**
	 * 获取最近的红包流水记录
	 * @param num
	 * @return
	 */
	List<UserRedpacketLog> getLatestUserRedpacketLog(int num);

	/**
	 * 用户红包
	 * @param uid
	 * @return
	 */
	UserRedpacket getUserRedpacket(Long uid);

	/**
	 * 保存用户红包的开启记录
	 * @param uid
	 * @param tid
	 * @param dateCode
	 * @param amount 
	 * @param type 
	 * @param isFree 
	 * @param isCrit 
	 * @return
	 */
	long saveUseRedpacketOpenRecord(Long uid, Long tid, int dateCode, int isFree, int type, double amount, int isCrit);

	/**
	 * 获取用户红包开启记录列表
	 * @param uid
	 * @param uids
	 * @param dateCode 
	 * @return
	 */
	List<UserRedpacketOpenRecord> getUserRedpacketOpenRecordList(Long uid, List<Long> uids, int dateCode);

	/**
	 * 获取邀请奖励记录列表
	 * @param uid
	 * @param num
	 * @return
	 */
	List<UserRedpacketRewardRecord> getUserRedpacketRewardRecordList(Long uid, int num);

	/**
	 * 获取可领取的邀请奖励记录
	 * @param uid
	 * @param tid
	 * @return
	 */
	UserRedpacketRewardRecord getUserRedpacketRewardRecord(Long uid, Long tid);

	/**
	 * 更新邀请奖励记录
	 * @param uid
	 * @param tid
	 * @return
	 */
	int updateUserRedpacketRewardRecord(Long uid, Long tid);

	/**
	 * 获取用户红包开启记录
	 * @param uid
	 * @param tid
	 * @param dateCode 
	 * @param status
	 * @return
	 */
	UserRedpacketOpenRecord getUserRedpacketOpenRecord(Long uid, Long tid, int dateCode, int status);

	/**
	 * 获取用户某日开启红包的次数
	 * @param uid
	 * @param dateCode
	 * @param tid 
	 * @return
	 */
	int getUserOpenTimes(Long uid, int dateCode, Long tid);

	/**
	 * 保存or更新用户开启红包次数
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	int saveOrUpdateUserRedpacketOpenTimes(Long uid, Long tid, int dateCode);

	/**
	 * 保存红包邀请奖励记录
	 * @param uid
	 * @param tid
	 * @param amount
	 * @return
	 */
	int saveUserRedpacketRewardRecord(Long uid, Long tid, double amount);

	/**
	 * 获取用户提现记录
	 * @param uid
	 * @param num
	 * @param type 
	 * @return
	 */
	List<UserRedpacketLog> getUserRedpacketLogsByUid(Long uid, int num, int type);
	
	/**
	 * 房间红包领取记录
	 * @param uid
	 * @param amt
	 * @param busId
	 * @param optId
	 * @return
	 */
	public int addBusRedpacket(long uid,double amt,int busId,long optId);
	
	/**
	 * 判断上机是否已领红包
	 * @param optId
	 * @return
	 */
	public int hasGetBusRedpacket(long optId);

	/**
	 * 获取用户开启红包次数
	 * @param uid
	 * @param dateCode
	 * @param isFree
	 * @param type
	 * @return
	 */
	int getOpenRedpacketTime(Long uid, int dateCode, int isFree, Integer type);

	/**
	 * 获取用户红包流水
	 * @param uid
	 * @param type
	 * @return
	 */
	UserRedpacketLog getUserRedpacketLog(long uid, int type);

	/**
	 * 根据ID获取用户红包开启记录
	 * @param id
	 * @return
	 */
	UserRedpacketOpenRecord getUserRedpacketOpenRecordById(Long id);

	/**
	 * 更新用户红包开启记录
	 * @param id
	 * @param openTime
	 * @param nextTime
	 * @return
	 */
	int updateUseRedpacketOpenRecord(Long id, Date openTime, Date nextTime);

	/**
	 * 获取用户未领取的记录
	 * @param uid
	 * @param uids
	 * @param dateCode 
	 * @return
	 */
	List<UserRedpacketOpenRecord> getNotGetRecordList(Long uid, List<Long> uids, int dateCode);

	/**
	 * 获取用户红包明细记录
	 * @param uid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<UserRedpacketLog> getRecords(Long uid, int page, int pageSize);

	/**
	 * 好友红包翻倍处理
	 * @param id
	 * @param newAmount
	 * @return
	 */
	int handleDouble(Long id, double newAmount);

	/**
	 * 获取红包
	 * @param redpacketId
	 * @return
	 */
	BusRedpacketRecord getBusRedpacket(long redpacketId);

	/**
	 * 是否已领取暴击红包
	 * @param redpacketId
	 * @return
	 */
	int hasGetSuperBusRedpacket(long redpacketId);
	
	/**
	 * 添加暴击红包记录
	 * @param redpacketId
	 * @param uid
	 * @return
	 */
	int addGetSuperBusRedpacket(long redpacketId,long uid);

	/**
	 * 获取
	 * @param type
	 * @param num
	 * @return
	 */
	List<UserRedpacketLog> getRecentUserRedpacketLogs(int type, int num);

	/**
	 * 获取用户提现总金额
	 * @param uid
	 * @return
	 */
	double getAllWithdrawAmt(Long uid);

	/**
	 * 获取用户红包领取总金额(提现除外)
	 * @param uid
	 * @return
	 */
	double getAllOtherAmt(Long uid);
}
