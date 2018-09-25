package com.imlianai.zjdoll.app.modules.support.userdoll.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.doll.user.UserDollDebris;
import com.imlianai.zjdoll.domain.doll.user.UserDollMonthCount;
import com.imlianai.zjdoll.domain.doll.user.UserDollWeekCount;
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
		StringBuffer sb = new StringBuffer();
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
	
	String getUserDollWeekCountList = "select * from user_doll_week_count where weekCode=? order by num desc, updateTime asc limit ?";
	@Override
	public List<UserDollWeekCount> getUserDollWeekCountList(int code, int num) {
		return jdbcHandler.queryBySql(UserDollWeekCount.class, getUserDollWeekCountList, code, num);
	}
	
	String getUserDollMonthCountList = "select * from user_doll_month_count where monthCode=? order by num desc, updateTime asc limit ?";
	@Override
	public List<UserDollMonthCount> getUserDollMonthCountList(int code, int num) {
		return jdbcHandler.queryBySql(UserDollMonthCount.class, getUserDollMonthCountList, code, num);
	}
	
	String getUserDollWeekCountByUidAndCode = "select * from user_doll_week_count where uid=? and weekCode=?";
	@Override
	public UserDollWeekCount getUserDollWeekCountByUidAndCode(Long uid, int code) {
		return jdbcHandler.queryOneBySql(UserDollWeekCount.class, getUserDollWeekCountByUidAndCode, uid, code);
	}
	
	String getUserDollMonthCountByUidAndCode = "select * from user_doll_month_count where uid=? and monthCode=?";
	@Override
	public UserDollMonthCount getUserDollMonthCountByUidAndCode(Long uid, int code) {
		return jdbcHandler.queryOneBySql(UserDollMonthCount.class, getUserDollMonthCountByUidAndCode, uid, code);
	}
	
	String saveUserDoll = "insert into user_doll(optId,uid,dollId,status,createTime,updateTime,remark,type,goodsType) values(?,?,?,?,now(),now(),?,?,?)";
	@Override
	public int saveUserDoll(UserDoll userDoll) {
		return jdbcHandler.executeSql(saveUserDoll, userDoll.getOptId(), userDoll.getUid(), 
				userDoll.getDollId(), userDoll.getStatus(), userDoll.getRemark(), userDoll.getType(), userDoll.getGoodsType());
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
	
	String saveOrUpdateUserDollWeekCount = "insert into user_doll_week_count(uid,weekCode,num,createTime,updateTime) values(?,?,?,now(),now())  on duplicate key update updateTime=now(),num=num+?";
	@Override
	public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num) {
		return jdbcHandler.executeSql(saveOrUpdateUserDollWeekCount, uid, weekCode, num, num);
	}
	
	String saveOrUpdateUserDollMonthCount = "insert into user_doll_month_count(uid,monthCode,num,createTime,updateTime) values(?,?,?,now(),now())  on duplicate key update updateTime=now(),num=num+?";
	@Override
	public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num) {
		return jdbcHandler.executeSql(saveOrUpdateUserDollMonthCount, uid, monthCode, num, num);
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
	
	@Override
	public int updateUserDollShipping(long id, String expressName,
			String trackingNum, int state) {
		return jdbcHandler.executeSql("update user_doll set updateTime=now(), status=? ,expressName=?,trackingNum=? where id=? limit 1", state,expressName,trackingNum,id);
	}

	String getUserDollDebrisByParams = "select * from user_doll_debris where uid=? and type=?";
	@Override
	public UserDollDebris getUserDollDebrisByParams(Long uid, int type) {
		return jdbcHandler.queryOneBySql(UserDollDebris.class, getUserDollDebrisByParams, uid, type);
	}

	String getNewestUserDollByType = "select * from user_doll where type=?";
	@Override
	public UserDoll getNewestUserDollByType(Integer dollId, int type) {
		StringBuilder sbSQL = new StringBuilder(getNewestUserDollByType);
		if(dollId != null && dollId.longValue() > 0) {
			sbSQL.append(" and dollId != ?");
			sbSQL.append(" order by createTime desc limit 1");
			return jdbcHandler.queryOneBySql(UserDoll.class, sbSQL.toString(), type, dollId);
		}
		sbSQL.append(" order by createTime desc limit 1");
		return jdbcHandler.queryOneBySql(UserDoll.class, sbSQL.toString(), type);
	}

	String getUserDollSizeByParamsAfter130 = "select count(1) from user_doll where uid=?";
	@Override
	public int getUserDollSizeByParamsAfter130(Long uid, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(getUserDollSizeByParams);
		if(status == -1) { // 全部
			sb.append(" and status>=?");
			status = 0;
		} else {
			if(status == 8) { // 成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=2 or status=4)");
				status = 1;
			} else if(status == 3){ 
				//sb.append(" and (status=? or status=5 or status=1 or status=2 or status=4)"); // 已兑换、已回收、成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=5)"); // 已兑换、已回收
			} else {
				sb.append(" and status=?");
			}
		}
		return jdbcHandler.queryCount(sb.toString(), uid, status);
	}
}
