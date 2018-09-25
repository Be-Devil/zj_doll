package com.imlianai.zjdoll.app.modules.core.trade.catalog.dao;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardExpirePushLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardReceiveAwardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayAwardRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayRecord;

@Repository
public class ChargeCatalogDaoImpl implements ChargeCatalogDao {

	private static final BaseLogger logger = BaseLogger
			.getLogger(ChargeCatalogDao.class);

	@Resource
	private JdbcHandler jdbcHandler;

	private static String getCatalogsSql = "select * from charge_catalog where type=? and isFirst=? and `valid`>0 order by `index`";

	@Override
	public List<ChargeCatalog> getCatalogs(int type,int isFirst) {
		try {
			return jdbcHandler.queryBySql(ChargeCatalog.class, getCatalogsSql,
					type,isFirst);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	private static String getCatalogsSql1 = "select * from charge_catalog where type=? and isFirst=? and tag=? and `valid`>0 order by `index`";

	@Override
	public List<ChargeCatalog> getCatalogs(int type, int tag, int isFirst) {
		try {
			return jdbcHandler.queryBySql(ChargeCatalog.class, getCatalogsSql1,
					type,isFirst,tag);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	private static String getCatalogSql = "select * from charge_catalog where code=? and `valid`>0";

	@Override
	public ChargeCatalog getCatalog(int code) {
		try {
			return jdbcHandler.queryOneBySql(ChargeCatalog.class,
					getCatalogSql, code);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	private static String getChargeCodeByChannelSql = "select code from mobile_charge_ios_catalog where channel=? limit 1";

	@Override
	public int getChargeCodeByChannel(String channel) {
		try {
			Integer id = jdbcHandler.queryOneBySql(Integer.class,
					getChargeCodeByChannelSql, channel);
			if (id == null)
				return 0;
			return id;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	String getWeekMonthCardCatalogs = "select * from charge_catalog where type=? and (isFirst=2 or isFirst=3) and `valid`>0 order by `index`";
	@Override
	public List<ChargeCatalog> getWeekMonthCardCatalogs(int chargeOsType) {
		return jdbcHandler.queryBySql(ChargeCatalog.class, getWeekMonthCardCatalogs, chargeOsType);
	}
	
	String getNewestChargeCardLog = "select * from charge_card_log where uid=? and type=? order by endDate desc limit 1";
	@Override
	public ChargeCardLog getNewestChargeCardLog(Long uid, int isFirst) {
		return jdbcHandler.queryOneBySql(ChargeCardLog.class, getNewestChargeCardLog, uid, isFirst);
	}
	
	String saveChargeCardLog = "insert into charge_card_log(uid,startDate,endDate,type,createTime) values(?,?,?,?,now())";
	@Override
	public int saveChargeCardLog(int isFirst, long uid, int startDate, int endDate) {
		return jdbcHandler.executeSql(saveChargeCardLog, uid, startDate, endDate, isFirst);
	}
	
	String getChargeCardReceiveAwardLog = "select * from charge_card_receive_award_log where uid=? and dateCode=? and type=?";
	@Override
	public ChargeCardReceiveAwardLog getChargeCardReceiveAwardLog(long uid, int dateCode, int type) {
		return jdbcHandler.queryOneBySql(ChargeCardReceiveAwardLog.class, getChargeCardReceiveAwardLog, uid, dateCode, type);
	}
	
	String saveChargeCardReceiveAwardLog = "insert into charge_card_receive_award_log(uid,dateCode,type,createTime) values(?,?,?,now())";
	@Override
	public int saveChargeCardReceiveAwardLog(long uid, int dateCode, int type) {
		return jdbcHandler.executeSql(saveChargeCardReceiveAwardLog, uid, dateCode, type);
	}
	
	String getChargeCardExpirePushLog = "select * from charge_card_expire_push_log where ccardLogId=?";
	@Override
	public ChargeCardExpirePushLog getChargeCardExpirePushLog(Long id) {
		return jdbcHandler.queryOneBySql(ChargeCardExpirePushLog.class, getChargeCardExpirePushLog, id);
	}
	
	String saveChargeCardExpirePushLog = "insert into charge_card_expire_push_log(ccardLogId,uid,pushTime) values(?,?,now())";
	@Override
	public int saveChargeCardExpirePushLog(Long id, long uid) {
		return jdbcHandler.executeSql(saveChargeCardExpirePushLog, id, uid);
	}
	
	String getValidChargeCardLogsByType = "select * from charge_card_log where type=? and endDate >= ?";
	@Override
	public List<ChargeCardLog> getValidChargeCardLogsByType(int type, int dateCode) {
		return jdbcHandler.queryBySql(ChargeCardLog.class, getValidChargeCardLogsByType, type, dateCode);
	}
	
	String getNotReceiveAwardUids = "select uid from charge_card_receive_award_log where dateCode=? and type=? and uid in(";
	@Override
	public List<Long> getNotReceiveAwardUids(List<Long> uids, int type, int dateCode) {
		StringBuilder sbSql = new StringBuilder(getNotReceiveAwardUids);
		final int size = uids.size();
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sbSql.append(uids.get(i) + ")");
			} else {
				sbSql.append(uids.get(i) + ",");
			}
		}
		return jdbcHandler.queryBySql(Long.class, sbSql.toString(), dateCode, type);
	}
	
	String getChargeDayRecord = "select * from charge_day_record where uid=? and dateCode=?";
	@Override
	public ChargeDayRecord getChargeDayRecord(long uid, int dateCode) {
		return jdbcHandler.queryOneBySql(ChargeDayRecord.class, getChargeDayRecord, uid, dateCode);
	}
	
	String getChargeDayAwardRecord = "select * from charge_day_award_record where uid=? and dateCode=? and amount=?";
	@Override
	public ChargeDayAwardRecord getChargeDayAwardRecord(long uid, int dateCode, int amount) {
		return jdbcHandler.queryOneBySql(ChargeDayAwardRecord.class, getChargeDayAwardRecord, uid, dateCode, amount);
	}
	
	String saveChargeDayAwardRecord = "insert into charge_day_award_record(uid,dateCode,amount,remark,createTime) values(?,?,?,?,now())";
	@Override
	public int saveChargeDayAwardRecord(long uid, int dateCode, int amount, String remark) {
		return jdbcHandler.executeSql(saveChargeDayAwardRecord, uid, dateCode, amount, remark);
	}
	
	String saveOrUpdateChargeDayRecord = "insert into charge_day_record(uid,dateCode,cost,createTime,updateTime) values(?,?,?,now(),now()) on duplicate key update cost=cost+?,updateTime=now()";
	@Override
	public int saveOrUpdateChargeDayRecord(long uid, int dateCode, double cost) {
		return jdbcHandler.executeSql(saveOrUpdateChargeDayRecord, uid, dateCode, cost, cost);
	}
	
	String getUserValidChargeCardLogsByUid = "select * from charge_card_log where uid=? and endDate >= ?";
	@Override
	public List<ChargeCardLog> getUserValidChargeCardLogsByUid(Long uid, int dateCode) {
		return jdbcHandler.queryBySql(ChargeCardLog.class, getUserValidChargeCardLogsByUid, uid, dateCode);
	}
}
