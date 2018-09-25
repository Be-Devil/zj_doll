package com.imlianai.zjdoll.app.modules.core.trade.catalog.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardExpirePushLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardReceiveAwardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayAwardRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayRecord;

public interface ChargeCatalogDao {

	/**
	 * 获取所有产品列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<ChargeCatalog> getCatalogs(int type,int isFirst);
	
	/**
	 * 获取快速充值列表
	 * @param type
	 * @param tag
	 * @param isFirst
	 * @return
	 */
	List<ChargeCatalog> getCatalogs(int type,int tag,int isFirst); 

	/**
	 * 获取指定类型的某个商品
	 * 
	 * @param code
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	ChargeCatalog getCatalog(int code);
	
	/**
	 * 根据渠道号获取充值初始code
	 * @param channel
	 * @return
	 */
	public int getChargeCodeByChannel(String channel);

	/**
	 * 获取周月卡充值列表
	 * @param chargeOsType
	 * @return
	 */
	List<ChargeCatalog> getWeekMonthCardCatalogs(int chargeOsType);

	/**
	 * 获取用户最新的周卡or月卡记录
	 * @param uid
	 * @param isFirst
	 * @return
	 */
	ChargeCardLog getNewestChargeCardLog(Long uid, int isFirst);

	/**
	 * 保存周月卡充值记录
	 * @param isFirst
	 * @param uid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int saveChargeCardLog(int isFirst, long uid, int startDate, int endDate);

	/**
	 * 获取周月卡充值登录领取记录
	 * @param uid
	 * @param dateCode
	 * @param type
	 * @return
	 */
	ChargeCardReceiveAwardLog getChargeCardReceiveAwardLog(long uid, int dateCode, int type);

	/**
	 * 保存周月卡充值登录领取记录
	 * @param uid
	 * @param currDate
	 * @param isFirst
	 * @return
	 */
	int saveChargeCardReceiveAwardLog(long uid, int dateCode, int type);

	/**
	 * 获取周月卡过期消息推送记录
	 * @param id
	 * @return
	 */
	ChargeCardExpirePushLog getChargeCardExpirePushLog(Long id);

	/**
	 * 保存周月卡过期消息推送记录
	 * @param id
	 * @param uid
	 * @return
	 */
	int saveChargeCardExpirePushLog(Long id, long uid);

	/**
	 * 获取有效的周月卡充值列表
	 * @param type
	 * @param dateCode
	 * @return
	 */
	List<ChargeCardLog> getValidChargeCardLogsByType(int type, int dateCode);

	/**
	 * 获取今日未领周月卡奖励的用户
	 * @param uids
	 * @param type
	 * @param dateCode
	 * @return
	 */
	List<Long> getNotReceiveAwardUids(List<Long> uids, int type, int dateCode);

	/**
	 * 获取用户日充值记录
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	ChargeDayRecord getChargeDayRecord(long uid, int dateCode);

	/**
	 * 用户日充值奖励记录
	 * @param uid
	 * @param dateCode
	 * @param amount
	 * @return
	 */
	ChargeDayAwardRecord getChargeDayAwardRecord(long uid, int dateCode, int amount);

	/**
	 * 保存用户日充值奖励记录
	 * @param uid
	 * @param dateCode
	 * @param amount
	 * @param remark 
	 * @return
	 */
	int saveChargeDayAwardRecord(long uid, int dateCode, int amount, String remark);

	/**
	 * 保存or更新日充值记录
	 * @param uid
	 * @param dateCode
	 * @param cost
	 * @return
	 */
	int saveOrUpdateChargeDayRecord(long uid, int dateCode, double cost);

	/**
	 * 获取用户有效的周月卡记录
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	List<ChargeCardLog> getUserValidChargeCardLogsByUid(Long uid, int dateCode);

}
