package com.imlianai.zjdoll.app.modules.support.busowner.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndexRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class BusOwnerDaoImpl implements BusOwnerDao {

	@Resource
	JdbcHandler jdbcHandler;

	private String getBusOwners = "select * from bus_owner where startCode=? and endCode=?";
	@Override
	public List<BusOwner> getBusOwners(int startCode, int endCode) {
		return jdbcHandler.queryBySql(BusOwner.class, getBusOwners, startCode, endCode);
	}
	
	private String saveOrUpdateCatTimes = "insert into bus_owner_user_index(busId,uid,startCode,endCode,catTimes,sucCatTimes,createTime,updateTime) values(?,?,?,?,?,?,now(),now()) "
			+ "on duplicate key update catTimes=catTimes+?,sucCatTimes=sucCatTimes+?,updateTime=now()"; 
	@Override
	public int saveOrUpdateCatTimes(int busId, long uid, int startCode, int endCode, int increCatTimes,
			int increSuccCatTimes) {
		return jdbcHandler.executeSql(saveOrUpdateCatTimes, busId, uid, startCode, endCode, increCatTimes, increSuccCatTimes, 
				increCatTimes, increSuccCatTimes);
	}
	
	private String getBusOwnerUserIndex = "select * from bus_owner_user_index where uid=? and startCode=? and endCode=?";
	@Override
	public BusOwnerUserIndex getBusOwnerUserIndex(Integer busId, long uid, int startCode, int endCode) {
		StringBuilder sbSQL = new StringBuilder(getBusOwnerUserIndex);
		if(busId != null && busId.intValue() > 0) {
			sbSQL.append(" and busId=?");
			return jdbcHandler.queryOneBySql(BusOwnerUserIndex.class, sbSQL.toString(), uid, startCode, endCode, busId);
		}
		return jdbcHandler.queryOneBySql(BusOwnerUserIndex.class, sbSQL.toString(), uid, startCode, endCode);
	}
	
	private String saveOrUpdateUserIndex = "insert into bus_owner_user_index(busId,uid,startCode,endCode,value,createTime,updateTime) values(?,?,?,?,?,now(),now()) "
			+ "on duplicate key update value=value+?,updateTime=now()"; 
	@Override
	public int saveOrUpdateUserIndex(int busId, long uid, int startCode, int endCode, int increValue) {
		return jdbcHandler.executeSql(saveOrUpdateUserIndex, busId, uid, startCode, endCode, increValue, increValue);
	}
	
	private String saveUserIndexRecord = "insert into bus_owner_user_index_record(optId,busId,uid,unionId,startCode,endCode,value,type,remark,createTime) "
			+ "values(?,?,?,?,?,?,?,?,?,now())";
	@Override
	public int saveUserIndexRecord(BusOwnerUserIndexRecord userIndexRecord, int increValue) {
		return jdbcHandler.executeSql(saveUserIndexRecord, userIndexRecord.getOptId(), userIndexRecord.getBusId(), userIndexRecord.getUid(), 
				userIndexRecord.getUnionId(), userIndexRecord.getStartCode(), userIndexRecord.getEndCode(), increValue, userIndexRecord.getType(), 
				userIndexRecord.getRemark());
	}
	
	private String getUserIndexRecordByParams = "select * from bus_owner_user_index_record where uid=? and startCode=? and endCode=? and unionId=?";
	@Override
	public BusOwnerUserIndexRecord getUserIndexRecordByParams(String unionId, Long uid, int startCode, int endCode, Integer busId) {
		StringBuilder sbSQL = new StringBuilder(getUserIndexRecordByParams);
		if(busId != null && busId.intValue() > 0) {
			sbSQL.append(" and busId=?");
			return jdbcHandler.queryOneBySql(BusOwnerUserIndexRecord.class, sbSQL.toString(), uid, startCode, endCode, unionId, busId);
		}
		return jdbcHandler.queryOneBySql(BusOwnerUserIndexRecord.class, sbSQL.toString(), uid, startCode, endCode, unionId);
	}
	
	private String getCurrCycleValue = "select sum(value) from bus_owner_user_index_record where uid=? and startCode=? and endCode=? and busId=? and type=1";
	@Override
	public int getCurrCycleValue(Long uid, int startCode, int endCode, int busId) {
		return jdbcHandler.queryCount(getCurrCycleValue, uid, startCode, endCode, busId);
	}
	
	private String getNewestBusOwner = "select * from bus_owner_user_index where busId=? and startCode=? "
			+ "and endCode=? order by value desc, updateTime asc limit 1";
	@Override
	public BusOwnerUserIndex getNewestBusOwner(int busId, int startCode, int endCode) {
		return jdbcHandler.queryOneBySql(BusOwnerUserIndex.class, getNewestBusOwner, busId, startCode, endCode);
	}
	
	private String saveBusOwner = "insert into bus_owner(busId,startCode,endCode,uid,createTime) values(?,?,?,?,now())";
	@Override
	public int saveBusOwner(Long uid, int busId, int startCode, int endCode) {
		return jdbcHandler.executeSql(saveBusOwner, busId, startCode, endCode, uid);
	}
	
	private String saveOrUpdateBusIncome = "insert into bus_owner_bus_income(busId,startCode,endCode,coin,createTime,updateTime) values(?,?,?,?,now(),now())"
			+ "on duplicate key update coin=coin+?,updateTime=now()"; 
	@Override
	public int saveOrUpdateBusIncome(int busId, int startCode, int endCode, int coin) {
		return jdbcHandler.executeSql(saveOrUpdateBusIncome, busId, startCode, endCode, coin, coin);
	}
	
	private String saveBusIncomeRecord = "insert into bus_owner_bus_income_record(optId,busId,startCode,endCode,coin,remark,createTime) values(?,?,?,?,?,?,now())";
	@Override
	public int saveBusIncomeRecord(int busId, long optId, int coin, int startCode, int endCode, String remark) {
		return jdbcHandler.executeSql(saveBusIncomeRecord, optId, busId, startCode, endCode, coin, remark);
	}
	
	private String getBusOwnersByUid = "select * from bus_owner where uid=? and startCode=? and endCode=?";
	@Override
	public List<BusOwner> getBusOwnersByUid(long uid, int startCode, int endCode) {
		return jdbcHandler.queryBySql(BusOwner.class, getBusOwnersByUid, uid, startCode, endCode);
	}
	
	private String getBusIncome = "select * from bus_owner_bus_income where busId=? and startCode=? and endCode=?";
	@Override
	public BusOwnerBusIncome getBusIncome(int busId, int startCode, int endCode) {
		return jdbcHandler.queryOneBySql(BusOwnerBusIncome.class, getBusIncome, busId, startCode, endCode);
	}
	
	private String getBusIncomeByBusIds = "select * from bus_owner_bus_income where startCode=? and endCode=? and busId in(";
	@Override
	public List<BusOwnerBusIncome> getBusIncomeByBusIds(List<Integer> mdDollBusIds, int startCode, int endCode) {
		StringBuilder sbSQL = new StringBuilder(getBusIncomeByBusIds);
		int SIZE = mdDollBusIds.size();
		for(int i = 0; i < SIZE; i++) {
			if(i == SIZE - 1) {
				sbSQL.append(mdDollBusIds.get(i) + ")");
			} else {
				sbSQL.append(mdDollBusIds.get(i) + ",");
			}
		}
		sbSQL.append(" order by coin desc, busId asc");
		return jdbcHandler.queryBySql(BusOwnerBusIncome.class, sbSQL.toString(), startCode, endCode);
	}
	
	private String getBusOwnerUserIndexList = "select * from bus_owner_user_index where busId=? and startCode=? and endCode=? order by value desc, updateTime asc limit ?";
	@Override
	public List<BusOwnerUserIndex> getBusOwnerUserIndexList(int busId, int startCode, int endCode, int size) {
		return jdbcHandler.queryBySql(BusOwnerUserIndex.class, getBusOwnerUserIndexList, busId, startCode, endCode, size);
	}
	
	private String getBusOwnerUserIndexRecordByParams = "select * from bus_owner_user_index_record where uid=? and startCode=? and endCode=? and type=?";
	@Override
	public List<BusOwnerUserIndexRecord> getBusOwnerUserIndexRecordByParams(Long uid, int startCode, int endCode,
			int type, Integer busId) {
		StringBuilder sbSQL = new StringBuilder(getBusOwnerUserIndexRecordByParams);
		if(busId != null && busId.intValue() > 0) {
			sbSQL.append(" and busId=?");
			sbSQL.append(" order by createTime desc");
			return jdbcHandler.queryBySql(BusOwnerUserIndexRecord.class, sbSQL.toString(), uid, startCode, endCode, type, busId); 
		}
		sbSQL.append(" order by createTime desc");
		return jdbcHandler.queryBySql(BusOwnerUserIndexRecord.class, sbSQL.toString(), uid, startCode, endCode, type);
	}
	
	private String getUserIndexRankingList = "select * from bus_owner_user_index where startCode=? and endCode=? order by value desc, updateTime asc limit ?";
	@Override
	public List<BusOwnerUserIndex> getBusOwnerUserIndexList(int startCode, int endCode, int size) {
		return jdbcHandler.queryBySql(BusOwnerUserIndex.class, getUserIndexRankingList, startCode, endCode, size);
	}
	
	private String getUserIndexByType = "select sum(value) from bus_owner_user_index_record where uid=? and startCode=? and endCode=? and type=? and busId=?";
	@Override
	public int getUserIndexByType(Long uid, int startCode, int endCode, int type, int busId) {
		return jdbcHandler.queryCount(getUserIndexByType, uid, startCode, endCode, type, busId);
	}
	
	private String getBusOwnerBusIds = "select busId from bus_owner_user_index where startCode=? and endCode=? group by busId";
	@Override
	public List<Integer> getBusOwnerBusIds(int startCode, int endCode) {
		return jdbcHandler.queryBySql(Integer.class, getBusOwnerBusIds, startCode, endCode);
	}
	
	private String saveBusOwnerUserSupportRecord = "insert into bus_owner_user_support_record(busId,uid,unionId,startCode,endCode,createTime) values(?,?,?,?,?,now())";
	@Override
	public int saveBusOwnerUserSupportRecord(int busId, Long uid, String unionId, int startCode, int endCode) {
		return jdbcHandler.executeSql(saveBusOwnerUserSupportRecord, busId, uid, unionId, startCode, endCode);
	}
}
