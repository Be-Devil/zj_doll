package com.imlianai.zjdoll.app.modules.core.doll.record;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollAppealRecord;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.DollSuccessOptRecord;
import com.imlianai.zjdoll.domain.doll.WatchRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.core.doll.vo.UserAbandonSummry;

@Repository
public class DollRecordDaoImpl implements DollRecordDao {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DollRecordDaoImpl.class);

	@Resource
	JdbcHandler jdbcHandler;

	String saveDollOptRecord = "insert into doll_opt_record(uid,busId,logId,dollId,cost,startTime,result,deviceCompany) values(?,?,?,?,?,now(),?,?)";
	@Override
	public int saveDollOptRecord(DollOptRecord record) {
		return jdbcHandler.executeSql(saveDollOptRecord, record.getUid(), record.getBusId(), record.getLogId(), record.getDollId(),
				record.getCost(), record.getResult(), record.getDeviceCompany());
	}
	
	String getOptRecords = "select * from doll_opt_record where uid=?";
	@Override
	public List<DollOptRecord> getOptRecords(long lastId, Long uid, int pageSize) {
		LOG.info("getOptRecords:" + lastId + "-" + uid);
		StringBuffer sb = new StringBuffer();
		sb.append(getOptRecords);
		if(lastId == 0) {
			sb.append(" and optId>?");
		} else {
			sb.append(" and optId<?");
		}
		sb.append(" order by optId desc limit ?");
		return jdbcHandler.queryBySql(DollOptRecord.class, sb.toString(), uid, lastId, pageSize);
	}
	
	String getOptRecord = "select * from doll_opt_record where optId=?";
	@Override
	public DollOptRecord getOptRecord(long optId) {
		return jdbcHandler.queryOneBySql(DollOptRecord.class, getOptRecord, optId);
	}
	
	String saveDollAppealRecord = "insert into doll_appeal_record(uid,optId,reason,status,createTime,updateTime) values(?,?,?,0,now(),now())";
	@Override
	public int saveDollAppealRecord(long optId, Long uid, String reason) {
		return jdbcHandler.executeSql(saveDollAppealRecord, uid, optId, reason);
	}
	
	String getSuccOptRecord = "select * from doll_success_opt_record order by createTime desc limit ?";
	@Override
	public List<DollSuccessOptRecord> getSuccOptRecord(int num) {
		return jdbcHandler.queryBySql(DollSuccessOptRecord.class, getSuccOptRecord, num);
	}
	String addWatchRecord = "insert into doll_watch_record(uid,busId,time) values(?,?,now())  on duplicate key update busId=? ,time=now()";
	String addWatchRecordLog = "insert into doll_watch_record_log(uid,busId,time,dateCol) values(?,?,now(),DATE_FORMAT(now(),'%Y-%m-%d'))";
	@Override
	public int addWatchRecord(long uid, int busId) {
		int flag=jdbcHandler.executeSql(addWatchRecord, uid,busId,busId);
		jdbcHandler.executeSql(addWatchRecordLog, uid,busId);
		return flag;
	}
	String addApplyLog = "INSERT INTO `doll_apply_log`(`uid`,`busId`,`company`,`deviceId`,`res`,`time`)VALUES(?,?,?,?,?,now());";
	@Override
	public void addApplyLog(long uid, int busId,int company, String deviceId, String res) {
		jdbcHandler.executeSql(addApplyLog, uid,busId,company,deviceId,res);
	}
	
	String updateDollOptRecord = "update doll_opt_record set result=?,endTime=now() where optId=?";
	@Override
	public int updateDollOptRecord(DollOptRecord record) {
		return jdbcHandler.executeSql(updateDollOptRecord, record.getResult(), record.getOptId());
	}
	
	String getAppealRecord = "select * from doll_appeal_record where optId=?";
	@Override
	public DollAppealRecord getAppealRecord(long optId) {
		return jdbcHandler.queryOneBySql(DollAppealRecord.class, getAppealRecord, optId);
	}
	
	String saveDollSucOptRecord = "insert into doll_success_opt_record(optId,uid,dollId,busId,createTime) values(?,?,?,?,now())";
	@Override
	public int saveDollSucOptRecord(long optId, long uid, int dollId,int busId) {
		return jdbcHandler.executeSql(saveDollSucOptRecord, optId, uid, dollId, busId);
	}
	
	String getOptRecordByLogId = "select * from doll_opt_record where logId=? and deviceCompany=?";
	@Override
	public DollOptRecord getOptRecordByLogId(long logId, int deviceCompany) {
		return jdbcHandler.queryOneBySql(DollOptRecord.class, getOptRecordByLogId, logId, deviceCompany);
	}
	
	String getBusRewardRecords = "select * from doll_success_opt_record where busId=? order by createTime desc limit ?,?";
	@Override
	public List<DollSuccessOptRecord> getBusRewardRecords(int busId, int page,
			int pageSize) {
		return jdbcHandler.queryBySql(DollSuccessOptRecord.class, getBusRewardRecords,busId,(page-1)*pageSize,pageSize);
	}
	@Override
	public List<WatchRecord> getWatchList(int busId, int page, int pageSize) {
		return jdbcHandler.queryBySql(WatchRecord.class, "select * from doll_watch_record where busId =? order by time desc limit ? ,?",busId,(page-1)*pageSize,pageSize);
	}
	@Override
	public int getWatchBus(long uid) {
		return jdbcHandler.queryCount("select busId from doll_watch_record where uid=?", uid);
	}
	@Override
	public int deleteWatchRecord(long uid, int busId) {
		return jdbcHandler.executeSql("delete from doll_watch_record where uid=? and busId=?", uid,busId);
	}
	@Override
	public void addDailyPlayRecord(long uid, int isSuccess) {
		jdbcHandler.executeSql("insert into doll_bus_user_daily_record"
				+ " (uid,dateCol,total,success,time) "
				+ "value (?,DATE_FORMAT(now(),'%Y-%m-%d'),1,?,now()) on duplicate key update total=total+1,success=success+?", uid,isSuccess,isSuccess);
	}
	@Override
	public void addUserPlaySummry(long uid,long optId,int isSuccess){
		jdbcHandler.executeSql("insert into user_total_play_summry (uid,total,success,fail,lastOptId,time) "
				+ "value (?,1,?,?,?,now()) on duplicate key update total=total+1,success=success+?,fail=fail+?,lastOptId=?", uid,isSuccess==1?1:0,isSuccess==1?0:1,optId,isSuccess==1?1:0,isSuccess==1?0:1,optId);
	}
	
	@Override
	public int getDailyPlayCount(long uid) {
		return jdbcHandler.queryCount("select total from doll_bus_user_daily_record where uid=? and dateCol=DATE_FORMAT(now(),'%Y-%m-%d')", uid);
	}
	@Override
	public int updateVideoRecord(long optId, String url1, String url2, int state) {
		return jdbcHandler.executeSql("update doll_opt_record set videoUrl1=? ,videoUrl2=? ,state=? where optId=?", url1,url2,state,optId);
	}
	
	@Override
	public void addUserAbandonSummry(long uid,long optId){
		jdbcHandler.executeSql("insert into user_abandon_summry (uid,num,lastOptId,time) "
				+ "value (?,1,?,now()) on duplicate key update num=num+1,lastOptId=?", uid,optId,optId);
	}
	@Override
	public int getUserTotalPlayCount(long uid) {
		return jdbcHandler.queryCount("select total from user_total_play_summry where uid=?", uid);
	}
	@Override
	public UserAbandonSummry getUserAbandonSummry(long uid) {
		return jdbcHandler.queryOneBySql(UserAbandonSummry.class, "select * from user_abandon_summry where uid=?",uid);
	}
	
	@Override
	public int saveDollConsumeRecord(int dollId) {
		return jdbcHandler.executeSql("insert into doll_consume_record (dollId,year,month,day,count) values (?,DATE_FORMAT(now(),'%Y'),DATE_FORMAT(now(),'%m'),DATE_FORMAT(now(),'%d'),1) on duplicate key update count = count + 1", dollId);
	}
	@Override
	public void addUserContinueTime(long uid, boolean isInit) {
		if (isInit) {
			jdbcHandler.executeSql("insert into user_continue_play_summry (uid,num,time) values (?,1,now()) on duplicate key update num=1,time=now()", uid);
		}else{
			jdbcHandler.executeSql("insert into user_continue_play_summry (uid,num,time) values (?,1,now()) on duplicate key update num=num+1,time=now()", uid);
		}
	}
	@Override
	public int getUserContinueTime(long uid) {
		return jdbcHandler.queryCount("select num from user_continue_play_summry where uid=? limit 1", uid);
	}
	@Override
	public void updateUserAbandonSummry(long uid, int lastTotalNum) {
		jdbcHandler.executeSql("update user_abandon_summry set lastTotalNum=? where uid=?", lastTotalNum,uid);
	}
	@Override
	public int updateOptHasHandle(long optId) {
		return jdbcHandler.executeSql("update doll_opt_record set hasHandle=1 where optId=? and hasHandle=0", optId);
	}
	@Override
	public int addAndGetSuccess(int busId,int minute) {
		return jdbcHandler.queryCount("select count(0) from doll_opt_record where busId=? and result=1 and endTime>DATE_SUB(now(),INTERVAL ? MINUTE) and isStrong in (0,1,3)", busId,minute);
	}
	@Override
	public void updateOptRecordStrong(long optId, int isStrong) {
		jdbcHandler.executeSql("update doll_opt_record set isStrong=? where optId=? limit 1", isStrong ,optId);
	}
	
}
