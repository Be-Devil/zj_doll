package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserDayRechargeSum;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserRechargeSum;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain.Event20180306TwistedeggUserTwistedeggInfo;

@Repository
public class EventTwistedEgg20180305DaoImpl implements EventTwistedEgg20180305Dao {

	@Resource
	JdbcHandler jdbcHandler;

	private String getTwistedeggInfoList = "select * from event_20180306_twistedegg_user_twistedegginfo where uid=? and dateCode=?";
	@Override
	public List<Event20180306TwistedeggUserTwistedeggInfo> getTwistedeggInfoList(Long uid, int dateCode) {
		return jdbcHandler.queryBySql(Event20180306TwistedeggUserTwistedeggInfo.class, getTwistedeggInfoList, uid, dateCode);
	}
	
	private String saveOrUpdateUserRechargeSum = "insert into event_20180306_twistedegg_user_recharge_sum(uid,type,amount,createTime,updateTime) values(?,?,?,now(),now())"
			+ " on duplicate key update amount=amount+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserRechargeSum(long uid, int type, double cost) {
		return jdbcHandler.executeSql(saveOrUpdateUserRechargeSum, uid, type, cost, cost);
	}
	
	private String getUserDayRechargeSum = "select * from event_20180306_twistedegg_user_dayrecharge_sum where uid=? and dateCode=?";
	@Override
	public Event20180306TwistedeggUserDayRechargeSum getUserDayRechargeSum(long uid, int dateCode) {
		return jdbcHandler.queryOneBySql(Event20180306TwistedeggUserDayRechargeSum.class, getUserDayRechargeSum, uid, dateCode);
	}
	
	private String saveOrUpdateUserTwistedeggInfo = "insert into event_20180306_twistedegg_user_twistedegginfo(uid,tEggId,dateCode,createTime,updateTime) values(?,?,?,now(),now())"
			+ " on duplicate key update updateTime=now()";
	@Override
	public int saveOrUpdateUserTwistedeggInfo(long uid, int dateCode, int tEggId) {
		return jdbcHandler.executeSql(saveOrUpdateUserTwistedeggInfo, uid, tEggId, dateCode);
	}
	
	private String getUserRechargeSum = "select * from event_20180306_twistedegg_user_recharge_sum where uid=? and type=?";
	@Override
	public Event20180306TwistedeggUserRechargeSum getUserRechargeSum(Long uid, int type) {
		return jdbcHandler.queryOneBySql(Event20180306TwistedeggUserRechargeSum.class, getUserRechargeSum, uid, type);
	}
	
	private String getUserTwistedeggInfoByParams = "select * from event_20180306_twistedegg_user_twistedegginfo where uid=? and tEggId=? and dateCode=?";
	@Override
	public Event20180306TwistedeggUserTwistedeggInfo getUserTwistedeggInfoByParams(Long uid, int teggId, int dateCode) {
		return jdbcHandler.queryOneBySql(Event20180306TwistedeggUserTwistedeggInfo.class, getUserTwistedeggInfoByParams, uid, teggId, dateCode);
	}
	
	private String updateUserTwistedEggInfoStatus = "update event_20180306_twistedegg_user_twistedegginfo set status=1,updateTime=now() where uid=? and tEggId=? and dateCode=?";
	@Override
	public int updateUserTwistedEggInfoStatus(Long uid, int teggId, int dateCode) {
		return jdbcHandler.executeSql(updateUserTwistedEggInfoStatus, uid, teggId, dateCode);
	}
	
	private String saveOrUpdateUserDayRechargeSum = "insert into event_20180306_twistedegg_user_dayrecharge_sum(uid,dateCode,amount,createTime,updateTime) values(?,?,?,now(),now())"
			+ " on duplicate key update amount=amount+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserDayRechargeSum(long uid, int dateCode, double cost) {
		return jdbcHandler.executeSql(saveOrUpdateUserDayRechargeSum, uid, dateCode, cost, cost);
	}
	
	private String saveUserRechargeRecord = "insert into event_20180306_twistedegg_user_recharge_record(uid,type,dateCode,amount,remark,createTime) values(?,?,?,?,?,now())";
	@Override
	public int saveUserRechargeRecord(Long uid, int type, int dateCode, double amount, String remark) {
		return jdbcHandler.executeSql(saveUserRechargeRecord, uid, type, dateCode, amount, remark);
	}
	
	private String handleExpire = "update event_20180306_twistedegg_user_twistedegginfo set status=2,updateTime=now() where status=0 or status=1";
	@Override
	public int handleExpire() {
		return jdbcHandler.executeSql(handleExpire);
	}
}
