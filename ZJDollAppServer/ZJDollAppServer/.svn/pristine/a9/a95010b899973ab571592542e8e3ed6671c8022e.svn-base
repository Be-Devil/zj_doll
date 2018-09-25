package com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.domain.Event20180207NewyearRechargeUserBlessing;

@Repository
public class EventNewyearRecharge20180207DaoImpl implements EventNewyearRecharge20180207Dao {

	@Resource
	JdbcHandler jdbcHandler;

	String saveOrUpdateNewyearrechargeUserRechargeSum = "insert into event_20180207_newyearrecharge_user_recharge_sum(uid,amount,createTime,updateTime) values(?,?,now(),now()) "
			+ " on duplicate key update amount=amount+?,updateTime=now()";
	@Override
	public int saveOrUpdateNewyearrechargeUserRechargeSum(double cost, long uid) {
		return jdbcHandler.executeSql(saveOrUpdateNewyearrechargeUserRechargeSum, uid, cost, cost);
	}
	
	String getRechargeAmountSum = "select amount from event_20180207_newyearrecharge_user_recharge_sum where uid=?";
	@Override
	public double getRechargeAmountSum(long uid) {
		Double amount = jdbcHandler.queryOneBySql(Double.class, getRechargeAmountSum, uid);
		return amount == null ? 0 : amount.doubleValue();
	}
	
	String saveOrUpdateNewyearRechargeUserBlessing = "insert into event_20180207_newyearrecharge_user_blessing(uid,value,createTime,updateTime) values(?,?,now(),now()) "
			+ " on duplicate key update value=?,updateTime=now()";
	@Override
	public int saveOrUpdateNewyearRechargeUserBlessing(long uid, double value) {
		return jdbcHandler.executeSql(saveOrUpdateNewyearRechargeUserBlessing, uid, value, value);
	}
	
	String saveNewyearRechargeUserBlessingRecord = "insert into event_20180207_newyearrecharge_user_blessing_record(uid,value,remark,createTime) values(?,?,?,now())";
	@Override
	public int saveNewyearRechargeUserBlessingRecord(long uid, double value, String remark) {
		return jdbcHandler.executeSql(saveNewyearRechargeUserBlessingRecord, uid, value, remark);
	}
	
	String getUserBlessingList = "select * from event_20180207_newyearrecharge_user_blessing order by value desc,updateTime asc limit ?";
	@Override
	public List<Event20180207NewyearRechargeUserBlessing> getUserBlessingList(int num) {
		return jdbcHandler.queryBySql(Event20180207NewyearRechargeUserBlessing.class, getUserBlessingList, num);
	}
	
	String getUserBlessingByUid = "select * from event_20180207_newyearrecharge_user_blessing where uid=?";
	@Override
	public Event20180207NewyearRechargeUserBlessing getUserBlessingByUid(Long uid) {
		return jdbcHandler.queryOneBySql(Event20180207NewyearRechargeUserBlessing.class, getUserBlessingByUid, uid);
	}
	@Override
	public int getMyRankCount(double value){
		return jdbcHandler.queryCount("select count(0) from event_20180207_newyearrecharge_user_blessing where value>?", value);
	}
}
