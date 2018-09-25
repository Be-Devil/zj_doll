package com.imlianai.zjdoll.app.modules.support.redpacket.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.redpacket.BusRedpacketRecord;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketLog;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketOpenRecord;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketRewardRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class RedpacketDaoImpl implements RedpacketDao {
	
	@Resource
	JdbcHandler jdbcHandler;

	String saveOrUpdateUserRedpacket = "insert into user_red_packet(uid,amount,createTime,updateTime) values(?,?,now(),now()) on duplicate key update updateTime=now(),amount=amount+?";
	@Override
	public int saveOrUpdateUserRedpacket(long uid, double redpackAmt) {
		return jdbcHandler.executeSql(saveOrUpdateUserRedpacket, uid, redpackAmt, redpackAmt);
	}
	
	String saveUserRedpacketLog = "insert into user_red_packet_log(uid,amount,type,remark,createTime,dateCode,optId) values(?,?,?,?,now(),?,?)";
	@Override
	public int saveUserRedpacketLog(long uid, double redpackAmt, int type, String remark, int dateCode, long optId) {
		return jdbcHandler.executeSql(saveUserRedpacketLog, uid, redpackAmt, type, remark, dateCode, optId);
	}
	
	String getLatestUserRedpacketLog = "select * from user_red_packet_log order by createTime desc limit ?";
	@Override
	public List<UserRedpacketLog> getLatestUserRedpacketLog(int num) {
		return jdbcHandler.queryBySql(UserRedpacketLog.class, getLatestUserRedpacketLog, num);
	}
	
	String getUserRedpacket = "select * from user_red_packet where uid=?";
	@Override
	public UserRedpacket getUserRedpacket(Long uid) {
		return jdbcHandler.queryOneBySql(UserRedpacket.class, getUserRedpacket, uid);
	}
	
	String saveUseRedpacketOpenRecord = "insert into user_red_packet_open_record(uid,tid,dateCode,isFree,type,num,createTime,updateTime,isCrit) values(?,?,?,?,?,?,now(),now(),?)";
	@Override
	public long saveUseRedpacketOpenRecord(Long uid, Long tid, int dateCode, int isFree, int type, double amount, int isCri) {
		return jdbcHandler.executeSql(saveUseRedpacketOpenRecord, uid, tid, dateCode, isFree, type, amount, isCri);
	}
	
	String getUserRedpacketOpenRecordList = "select * from user_red_packet_open_record where uid=? and nextTime>now() and dateCode=? and tid in(";
	@Override
	public List<UserRedpacketOpenRecord> getUserRedpacketOpenRecordList(Long uid, List<Long> uids, int dateCode) {
		StringBuilder sb = new StringBuilder(getUserRedpacketOpenRecordList);
		final int SIZE = uids.size();
		for(int i = 0; i < SIZE; i++) {
			if(i == SIZE-1) {
				sb.append(uids.get(i) + ")");
			} else {
				sb.append(uids.get(i) + ",");
			}
		}
		return jdbcHandler.queryBySql(UserRedpacketOpenRecord.class, sb.toString(), uid, dateCode);
	}
	
	String getUserRedpacketRewardRecordList = "select * from user_red_packet_reward_record where uid=? order by createTime asc limit ?";
	@Override
	public List<UserRedpacketRewardRecord> getUserRedpacketRewardRecordList(Long uid, int num) {
		return jdbcHandler.queryBySql(UserRedpacketRewardRecord.class, getUserRedpacketRewardRecordList, uid, num);
	}
	
	String getUserRedpacketRewardRecord = "select * from user_red_packet_reward_record where uid=? and tid=? and status=0";
	@Override
	public UserRedpacketRewardRecord getUserRedpacketRewardRecord(Long uid, Long tid) {
		return jdbcHandler.queryOneBySql(UserRedpacketRewardRecord.class, getUserRedpacketRewardRecord, uid, tid);
	}
	
	String updateUserRedpacketRewardRecord = "update user_red_packet_reward_record set status=1, updateTime=now() where uid=? and tid=?";
	@Override
	public int updateUserRedpacketRewardRecord(Long uid, Long tid) {
		return jdbcHandler.executeSql(updateUserRedpacketRewardRecord, uid, tid);
	}
	
	String getUserRedpacketOpenRecord = "select * from user_red_packet_open_record where uid=? and tid=? and dateCode=? and status=? order by createTime desc limit 1";
	@Override
	public UserRedpacketOpenRecord getUserRedpacketOpenRecord(Long uid, Long tid, int dateCode, int status) {
		return jdbcHandler.queryOneBySql(UserRedpacketOpenRecord.class, getUserRedpacketOpenRecord, uid, tid, dateCode, status);
	}
	
	String getUserOpenTimes = "select times from user_red_packet_open_times where uid=? and dateCode=? and tid=?";
	@Override
	public int getUserOpenTimes(Long uid, int dateCode, Long tid) {
		return jdbcHandler.queryCount(getUserOpenTimes, uid, dateCode, tid);
	}
	
	String saveOrUpdateUserRedpacketOpenTimes = "insert into user_red_packet_open_times(uid,tid,dateCode,times,createTime,updateTime) values(?,?,?,1,now(),now()) on duplicate key update updateTime=now(),times=times+1";
	@Override
	public int saveOrUpdateUserRedpacketOpenTimes(Long uid, Long tid, int dateCode) {
		return jdbcHandler.executeSql(saveOrUpdateUserRedpacketOpenTimes, uid, tid, dateCode);
	}
	
	String saveUserRedpacketRewardRecord = "insert into user_red_packet_reward_record(uid,tid,amount,status,createTime,updateTime) values(?,?,?,0,now(),now())";
	@Override
	public int saveUserRedpacketRewardRecord(Long uid, Long tid, double amount) {
		return jdbcHandler.executeSql(saveUserRedpacketRewardRecord, uid, tid, amount);
	}
	
	String getUserRedpacketLogsByUid = "select * from user_red_packet_log where uid=? and type=? order by createTime desc limit ?";
	@Override
	public List<UserRedpacketLog> getUserRedpacketLogsByUid(Long uid, int num, int type) {
		return jdbcHandler.queryBySql(UserRedpacketLog.class, getUserRedpacketLogsByUid, uid, type, num);
	}
	@Override
	public int addBusRedpacket(long uid, double amt, int busId, long optId) {
		return jdbcHandler.executeSql("insert into bus_redpacket_record (uid,busId,optId,amount,createTime) value (?,?,?,?,now())", uid,busId,optId,amt);
	}
	@Override
	public int hasGetBusRedpacket(long optId) {
		return jdbcHandler.queryCount("select 1 from bus_redpacket_record where optId=?", optId);
	}
	@Override
	public BusRedpacketRecord getBusRedpacket(long redpacketId) {
		return jdbcHandler.queryOneBySql(BusRedpacketRecord.class,"select * from bus_redpacket_record where id=? limit 1", redpacketId);
	}
	String getOpenRedpacketTime = "select count(1) from user_red_packet_open_record where uid=? and dateCode=? and isFree=?";
	@Override
	public int getOpenRedpacketTime(Long uid, int dateCode, int isFree, Integer type) {
		StringBuilder sb = new StringBuilder(getOpenRedpacketTime);
		if(type != null && type.intValue() > 0) {
			sb.append(" and type=?");
			return jdbcHandler.queryCount(sb.toString(), uid, dateCode, isFree, type.intValue());
		}
		return jdbcHandler.queryCount(sb.toString(), uid, dateCode, isFree);
	}
	
	String getUserRedpacketLog = "select * from user_red_packet_log where uid=? and type=? limit 1";
	@Override
	public UserRedpacketLog getUserRedpacketLog(long uid, int type) {
		return jdbcHandler.queryOneBySql(UserRedpacketLog.class, getUserRedpacketLog, uid, type);
	}
	
	String getUserRedpacketOpenRecordById = "select * from user_red_packet_open_record where id=?";
	@Override
	public UserRedpacketOpenRecord getUserRedpacketOpenRecordById(Long id) {
		return jdbcHandler.queryOneBySql(UserRedpacketOpenRecord.class, getUserRedpacketOpenRecordById, id);
	}
	
	String updateUseRedpacketOpenRecord = "update user_red_packet_open_record set openTime=?,nextTime=?,status=1 where id=?";
	@Override
	public int updateUseRedpacketOpenRecord(Long id, Date openTime, Date nextTime) {
		return jdbcHandler.executeSql(updateUseRedpacketOpenRecord, openTime, nextTime, id);
	}
	
	String getNotGetRecordList = "select * from user_red_packet_open_record where uid=? and status=0 and dateCode=? and tid in(";
	@Override
	public List<UserRedpacketOpenRecord> getNotGetRecordList(Long uid, List<Long> uids, int dateCode) {
		StringBuilder sb = new StringBuilder(getNotGetRecordList);
		final int SIZE = uids.size();
		for(int i = 0; i < SIZE; i++) {
			if(i == SIZE-1) {
				sb.append(uids.get(i) + ")");
			} else {
				sb.append(uids.get(i) + ",");
			}
		}
		return jdbcHandler.queryBySql(UserRedpacketOpenRecord.class, sb.toString(), uid, dateCode);
	}
	
	String getRecords = "select * from user_red_packet_log where uid=? order by id desc limit ?,? ";
	@Override
	public List<UserRedpacketLog> getRecords(Long uid, int page, int pageSize) {
		return jdbcHandler.queryBySql(UserRedpacketLog.class, getRecords, uid, (page-1)*pageSize, pageSize);
	}
	
	String handleDouble = "update user_red_packet_open_record set num=?,doubleFlag=1 where id=? and doubleFlag=0";
	@Override
	public int handleDouble(Long id, double newAmount) {
		return jdbcHandler.executeSql(handleDouble, newAmount, id);
	}
	@Override
	public int hasGetSuperBusRedpacket(long redpacketId) {
		return jdbcHandler.queryCount("select 1 from bus_super_redpacket_log where redpecketId=? limit 1", redpacketId);
	}
	@Override
	public int addGetSuperBusRedpacket(long redpacketId, long uid) {
		return jdbcHandler.executeSql("insert into bus_super_redpacket_log (redpecketId,uid,time) value (?,?,now())", redpacketId,uid);
	}
	
	private String getRecentUserRedpacketLogs = "select * from user_red_packet_log where type=? order by createTime desc limit ?";
	@Override
	public List<UserRedpacketLog> getRecentUserRedpacketLogs(int type, int num) {
		return jdbcHandler.queryBySql(UserRedpacketLog.class, getRecentUserRedpacketLogs, type, num);
	}
	
	private String getAllWithdrawAmt = "select -sum(amount) from user_red_packet_log where uid=? and type=3";
	@Override
	public double getAllWithdrawAmt(Long uid) {
		Double amt = jdbcHandler.queryOneBySql(Double.class, getAllWithdrawAmt, uid);
		return amt == null ? 0 : amt.doubleValue();
	}
	
	private String getAllOtherAmt = "select sum(amount) from user_red_packet_log where uid=? and type != 3";
	@Override
	public double getAllOtherAmt(Long uid) {
		Double amt = jdbcHandler.queryOneBySql(Double.class, getAllOtherAmt, uid);
		return amt == null ? 0 : amt.doubleValue();
	}
}
