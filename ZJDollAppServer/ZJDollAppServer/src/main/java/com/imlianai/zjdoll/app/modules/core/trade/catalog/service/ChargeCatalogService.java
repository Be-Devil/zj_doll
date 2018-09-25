package com.imlianai.zjdoll.app.modules.core.trade.catalog.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayAwardRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayRecord;

public interface ChargeCatalogService {
	/**
	 * 获取所有产品列表
	 * @param type
	 * @param isFirst 是否是首冲优惠列表 1-是 0-否
	 * @return
	 */
	List<ChargeCatalog> getCatalogs(int type,int isFirst);
	
	/**
	 * 获取首冲优惠充值列表
	 * @param type
	 * @param uid
	 * @return
	 */
	List<ChargeCatalog> getFirstPayCatalogs(int type,long uid);
	
	/**
	 * 获取快速充值列表
	 * @param type
	 * @return
	 */
	List<ChargeCatalog> getQuickCatalogs(int type);
	/**
	 * 处理ios马甲充值代码
	 * @param channel
	 * @param list
	 * @return
	 */
	List<ChargeCatalog> handleIosCatalogs(String channel,List<ChargeCatalog> list);

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
	 * 获取周月卡充值列表
	 * @param chargeOsType
	 * @param uid
	 * @return
	 */
	List<ChargeCatalog> getWeekMonthCardCatalogs(int chargeOsType, Long uid);

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
	 * 登录-周月卡奖励处理
	 * @param uid
	 */
	void handleLoginReward(Long uid);

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
	 * 保存or更新日充值奖励记录
	 * @param uid
	 * @param dateCode
	 * @param cost
	 * @return
	 */
	int saveOrUpdateChargeDayRecord(long uid, int dateCode, double cost);

	/**
	 * 周月卡是否有效
	 * @param uid 
	 * @return
	 */
	boolean isValid(Long uid);
}
