package com.imlianai.dollpub.app.modules.support.userdoll.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.doll.user.UserDollDebris;
import com.imlianai.dollpub.domain.doll.user.UserDollMonthCount;
import com.imlianai.dollpub.domain.doll.user.UserDollWeekCount;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserDollDaoImpl implements UserDollDao {

	@Resource
	JdbcHandler jdbcHandler;

	String getUserDollById = "select * from user_doll where id=?";
	@Override
	public UserDoll getUserDollById(long id) {
		return jdbcHandler.queryOneBySql(UserDoll.class, getUserDollById, id);
	}

	String updateUserDollStatus = "update user_doll set status=?,updateTime=now() where id=?";
	@Override
	public int updateUserDollStatus(long id, int status) {
		return jdbcHandler.executeSql(updateUserDollStatus, status, id);
	}
	
	String getExchangeUserDollList = "select * from user_doll where createTime<=DATE_ADD(CURRENT_DATE(), INTERVAL ? DAY) and status = 0";
	@Override
	public List<UserDoll> getExchangeUserDollList(int otherDays) {
		return jdbcHandler.queryBySql(UserDoll.class, getExchangeUserDollList, otherDays);
	}
	
	String getUserDollList = "select * from user_doll where uid=?";
	@Override
	public List<UserDoll> getUserDollList(Long userId, long lastId, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(getUserDollList);
		if(lastId == 0) {
			sb.append(" and id>?");
		} else {
			sb.append(" and id<?");
		}
		sb.append(" order by id desc limit ?");
		return jdbcHandler.queryBySql(UserDoll.class, sb.toString(), userId, lastId, pageSize);
	}
	
	String getDollCountByUid = "select count(1) from user_doll where uid=?";
	@Override
	public int getDollCountByUid(Long userId) {
		return jdbcHandler.queryCount(getDollCountByUid, userId);
	}
	
	String getShippingList = "select * from user_doll where uid=? and status=0 order by createTime asc";
	@Override
	public List<UserDoll> getShippingList(Long uid) {
		return jdbcHandler.queryBySql(UserDoll.class, getShippingList, uid);
	}
	
	String getDollCountByUserIdAndDollId = "select count(1) from user_doll where uid=? and dollId=? and status=0";
	@Override
	public int getDollCountByUserIdAndDollId(Long uid, long dollId) {
		return jdbcHandler.queryCount(getDollCountByUserIdAndDollId, uid, dollId);
	}
	
	String getUserDollWeekCountList = "select * from user_doll_week_count where weekCode=?";
	@Override
	public List<UserDollWeekCount> getUserDollWeekCountList(int code, int num, Integer groupId) {
		StringBuilder sbSQL = new StringBuilder(getUserDollWeekCountList);
		if(groupId != null && groupId.intValue() > 0) {
			sbSQL.append(" and groupId=? order by num desc, updateTime asc limit ?");
			return jdbcHandler.queryBySql(UserDollWeekCount.class, sbSQL.toString(), code, groupId.intValue(), num);
		} else {
			sbSQL.append(" order by num desc, updateTime asc limit ?");
			return jdbcHandler.queryBySql(UserDollWeekCount.class, sbSQL.toString(), code, num);
		}
	}
	
	String getUserDollMonthCountList = "select * from user_doll_month_count where monthCode=?";
	@Override
	public List<UserDollMonthCount> getUserDollMonthCountList(int code, int num, Integer groupId) {
		StringBuilder sbSQL = new StringBuilder(getUserDollMonthCountList);
		if(groupId != null && groupId.intValue() > 0) {
			sbSQL.append(" and groupId=? order by num desc, updateTime asc limit ?");
			return jdbcHandler.queryBySql(UserDollMonthCount.class, sbSQL.toString(), code, groupId.intValue(), num);
		} else {
			sbSQL.append(" order by num desc, updateTime asc limit ?");
			return jdbcHandler.queryBySql(UserDollMonthCount.class, sbSQL.toString(), code, num);
		}
	}
	
	String getUserDollWeekCountByUidAndCode = "select * from user_doll_week_count where uid=? and groupId=? and weekCode=?";
	@Override
	public UserDollWeekCount getUserDollWeekCountByUidAndCode(Long uid, int code, int groupId) {
		return jdbcHandler.queryOneBySql(UserDollWeekCount.class, getUserDollWeekCountByUidAndCode, uid, groupId, code);
	}
	
	String getUserDollMonthCountByUidAndCode = "select * from user_doll_month_count where uid=? and groupId=? and monthCode=?";
	@Override
	public UserDollMonthCount getUserDollMonthCountByUidAndCode(Long uid, int code, int groupId) {
		return jdbcHandler.queryOneBySql(UserDollMonthCount.class, getUserDollMonthCountByUidAndCode, uid, groupId, code);
	}
	
	String saveUserDoll = "insert into user_doll(optId,uid,dollId,agentId,agentName,status,createTime,updateTime,remark,type,goodsType) values(?,?,?,?,?,?,now(),now(),?,?,?)";
	@Override
	public int saveUserDoll(UserDoll userDoll) {
		return jdbcHandler.executeSql(saveUserDoll, userDoll.getOptId(), userDoll.getUid(), userDoll.getDollId(),userDoll.getAgentId(),
				userDoll.getAgentName() ,userDoll.getStatus(), userDoll.getRemark(), userDoll.getType(), userDoll.getGoodsType());
	}
	
	String getUserDollSizeByParams = "select count(1) from user_doll where uid=?";
	@Override
	public int getUserDollSizeByParams(Long uid, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(getUserDollSizeByParams);
		if(status == -1) { // 全部
			sb.append(" and status>=?");
			status = 0;
		} else {
			if(status == 8) { // 成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=2 or status=4)");
				status = 1;
			} else if(status == 3){ // 已兑换，已回收
				sb.append(" and (status=? or status=5)");
			} else {
				sb.append(" and status=?");
			}
		}
		return jdbcHandler.queryCount(sb.toString(), uid, status);
	}
	
	String getUserDollByParams = "select * from user_doll where uid=? and dollId=? and status=0 order by createTime asc limit ?";
	@Override
	public List<UserDoll> getUserDollByParams(Long uid, long dollId, int num) {
		return jdbcHandler.queryBySql(UserDoll.class, getUserDollByParams, uid, dollId, num); 
	}
	
	String saveOrUpdateUserDollWeekCount = "insert into user_doll_week_count(uid,weekCode,num,createTime,updateTime,groupId) values(?,?,?,now(),now(),?)  on duplicate key update updateTime=now(),num=num+?";
	@Override
	public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num, Integer groupId) {
		return jdbcHandler.executeSql(saveOrUpdateUserDollWeekCount, uid, weekCode, num, groupId, num);
	}
	
	String saveOrUpdateUserDollMonthCount = "insert into user_doll_month_count(uid,monthCode,num,createTime,updateTime,groupId) values(?,?,?,now(),now(),?)  on duplicate key update updateTime=now(),num=num+?";
	@Override
	public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num, Integer groupId) {
		return jdbcHandler.executeSql(saveOrUpdateUserDollMonthCount, uid, monthCode, num, groupId, num);
	}
	
	private String selectUserDollByDollState = "SELECT * from user_doll WHERE uid=?";
	@Override
	public List<UserDoll> getUserDollByState(long uid, int state) {
		StringBuilder sb = new StringBuilder();
		sb.append(selectUserDollByDollState);
		if(state == -1) {	//全部
			sb.append(" and status>=?");
			state = 0;
		}else {
			if(state == 8) { // 成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=2 or status=4)");
				state = 1;
			} else {
				sb.append(" and status=?");
			}
		}
		sb.append(" order by createTime desc");
		return jdbcHandler.queryBySql(UserDoll.class, sb.toString(), uid,state);
	}

	String getUserDollByIdAndUid = "select * from user_doll where uid=? and id=?";
	
	@Override
	public UserDoll getUserDollById(long uid, int id) {
		return jdbcHandler.queryOneBySql(UserDoll.class, getUserDollByIdAndUid, uid,id);
	}

	@Override
	public List<UserDoll> getUserDollByOptId(long optId) {
		return jdbcHandler.queryBySql(UserDoll.class,"SELECT * FROM user_doll WHERE optId=?",optId);
	}

	String getUserDollDebrisByUid = "select * from user_doll_debris where uid=?";
	@Override
	public List<UserDollDebris> getUserDollDebrisByUid(Long uid) {
		return jdbcHandler.queryBySql(UserDollDebris.class, getUserDollDebrisByUid, uid);
	}

	String updateUserDollDebris = "update user_doll_debris set num=num+? where uid=? and type=?";
	@Override
	public int updateUserDollDebris(Long uid, int type, int num) {
		return jdbcHandler.executeSql(updateUserDollDebris, num, uid, type);
	}

	String saveOrUpdateUserDollDebris = "insert into user_doll_debris(uid,type,num,createTime,updateTime) values(?,?,?,now(),now()) on duplicate key update updateTime=now(),num=num+?";
	@Override
	public int saveOrUpdateUserDollDebris(Long uid, int type, double num) {
		return jdbcHandler.executeSql(saveOrUpdateUserDollDebris, uid, type, num, num);
	}

	String saveUserDollDebrisLog = "insert into user_doll_debris_log(uid,type,num,remark,createTime) values(?,?,?,?,now())";
	@Override
	public int saveUserDollDebrisLog(Long uid, int type, double num, String remark) {
		return jdbcHandler.executeSql(saveUserDollDebrisLog, uid, type, num, remark);
	}
}
