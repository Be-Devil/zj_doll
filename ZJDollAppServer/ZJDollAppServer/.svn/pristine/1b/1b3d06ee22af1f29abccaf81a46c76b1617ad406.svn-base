package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserFortune;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserIncome;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserOptRecord;

@Repository
public class EventTwistedEggMachineDaoImpl implements EventTwistedEggMachineDao {

	@Resource
	JdbcHandler jdbcHandler;

	private String getTeggNum = "select num from event_twistedeggmachine_user_twistedegg_num where uid=?";
	@Override
	public int getTeggNum(Long uid) {
		return jdbcHandler.queryCount(getTeggNum, uid);
	}
	
	private String getRecentlyUserOptRecords = "select * from event_twistedeggmachine_user_opt_record";
	@Override
	public List<EventTwistedEggMachineUserOptRecord> getRecentlyUserOptRecords(int num, Long uid) {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(getRecentlyUserOptRecords);
		if(uid != null && uid.longValue() > 0) {
			sbSQL.append(" where uid=? order by createTime desc limit ?");
			return jdbcHandler.queryBySql(EventTwistedEggMachineUserOptRecord.class, sbSQL.toString(), uid, num);
		} else {
			sbSQL.append(" order by createTime desc limit ?");
			return jdbcHandler.queryBySql(EventTwistedEggMachineUserOptRecord.class, sbSQL.toString(), num);
		}
	}
	
	private String getPreviousUserFortune = "select * from event_twistedeggmachine_user_fortune where dateCode=? order by value desc, updateTime asc limit ?";
	@Override
	public List<EventTwistedEggMachineUserFortune> getPreviousUserFortune(int num, int dateCode) {
		return jdbcHandler.queryBySql(EventTwistedEggMachineUserFortune.class, getPreviousUserFortune, dateCode, num);
	}
	
	private String getUserFortuneByUid = "select * from event_twistedeggmachine_user_fortune where uid=? and dateCode=?";
	@Override
	public EventTwistedEggMachineUserFortune getUserFortuneByUid(Long uid, int dateCode) {
		return jdbcHandler.queryOneBySql(EventTwistedEggMachineUserFortune.class, getUserFortuneByUid, uid, dateCode);
	}
	
	private String updateUserTwistedEggNum = "update event_twistedeggmachine_user_twistedegg_num set num=num-?,updateTime=now() where uid=? and num-?>=0";
	@Override
	public int updateUserTwistedEggNum(Long uid, int num) {
		return jdbcHandler.executeSql(updateUserTwistedEggNum, num, uid, num);
	}
	
	private String saveOrUpdateUserTwistedEggNum = "insert into event_twistedeggmachine_user_twistedegg_num(uid,num,createTime,updateTime) values(?,?,now(),now())"
			+ " on duplicate key update num=num+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserTwistedEggNum(Long uid, int num) {
		return jdbcHandler.executeSql(saveOrUpdateUserTwistedEggNum, uid, num, num);
	}
	
	private String saveUserTwistedEggRecord = "insert into event_twistedeggmachine_user_twistedegg_record(uid,dateCode,num,remark,createTime) values(?,?,?,?,now())";
	@Override
	public int saveUserTwistedEggRecord(Long uid, int dateCode, int num, String remark) {
		return jdbcHandler.executeSql(saveUserTwistedEggRecord, uid, dateCode, num, remark);
	}
	
	private String saveUserOptRecord = "insert into event_twistedeggmachine_user_opt_record(uid,currTimes,type,awardType,awardId,awardName,awardCoin,remark,dateCode,isRobot,createTime) values"
			+ "(?,?,?,?,?,?,?,?,?,?,now())";
	@Override
	public int saveUserOptRecord(Long uid, int currTimes, int type, int awardType, int awardId, String awardName, int awardCoin,
			String remark, int dateCode, int isRobot) {
		return jdbcHandler.executeSql(saveUserOptRecord, uid, currTimes, type, awardType, awardId, awardName, awardCoin, remark, dateCode, isRobot);
	}
	
	private String getCurrTimes = "select currTimes from event_twistedeggmachine_user_opt_record where uid=? and type=? order by currTimes desc limit 1";
	@Override
	public int getCurrTimes(Long uid, int type) {
		return jdbcHandler.queryCount(getCurrTimes, uid, type);
	}
	
	private String getCurrCoinAwardSum = "select sum(awardCoin) from event_twistedeggmachine_user_opt_record where uid=? and dateCode=? and awardType=0";
	@Override
	public int getCurrCoinAwardSum(Long uid, int dateCode, int type) {
		StringBuilder sbSQL = new StringBuilder(getCurrCoinAwardSum);
		if(type == -1) {
			return jdbcHandler.queryCount(sbSQL.toString(), uid, dateCode);
		} else {
			sbSQL.append(" and type=?");
			return jdbcHandler.queryCount(sbSQL.toString(), uid, dateCode, type);
		}
	}
	
	private String saveOrUpdateUserFortune = "insert into event_twistedeggmachine_user_fortune(uid,value,createTime,updateTime,dateCode) values(?,?,now(),now(),?)"
			+ " on duplicate key update value=value+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserFortune(Long uid, int value, int dateCode) {
		return jdbcHandler.executeSql(saveOrUpdateUserFortune, uid, value, dateCode, value);
	}
	
	private String saveUserFortuneRecord = "insert into event_twistedeggmachine_user_fortune_record(uid,dateCode,value,remark,createTime) values(?,?,?,?,now())";
	@Override
	public int saveUserFortuneRecord(Long uid, int dateCode, int value, String remark) {
		return jdbcHandler.executeSql(saveUserFortuneRecord, uid, dateCode, value, remark);
	}
	
	private String getUserIncome = "select * from event_twistedeggmachine_user_income where uid=?";
	@Override
	public EventTwistedEggMachineUserIncome getUserIncome(long uid) {
		return jdbcHandler.queryOneBySql(EventTwistedEggMachineUserIncome.class, getUserIncome, uid);
	}
	
	private String saveOrUpdateUserIncome = "insert into event_twistedeggmachine_user_income(uid,value,createTime,updateTime) values(?,?,now(),now())"
			+ " on duplicate key update value=value+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserIncome(Long uid, int value) {
		return jdbcHandler.executeSql(saveOrUpdateUserIncome, uid, value, value);
	}
	
	private String saveUserIncomeRecord = "insert into event_twistedeggmachine_user_income_record(uid,dateCode,value,remark,createTime) values(?,?,?,?,now())";
	@Override
	public int saveUserIncomeRecord(Long uid, int value, int dateCode, String remark) {
		return jdbcHandler.executeSql(saveUserIncomeRecord, uid, dateCode, value, remark);
	}
	
	private String getCoinAwardSum = "select sum(awardCoin) from event_twistedeggmachine_user_opt_record where awardType=0 and type=?";
	@Override
	public int getCoinAwardSum(int type) {
		return jdbcHandler.queryCount(getCoinAwardSum, type);
	}
}
