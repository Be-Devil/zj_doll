package com.imlianai.dollpub.app.modules.core.doll.record;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.domain.doll.DollAppealRecord;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.DollSuccessOptRecord;
import com.imlianai.dollpub.domain.doll.WatchRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollRecordDaoImpl implements DollRecordDao {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DollRecordDaoImpl.class);

	@Resource
	JdbcHandler jdbcHandler;

	String saveDollOptRecord = "insert into doll_opt_record(uid,busId,logId,dollId,cost,startTime,result) values(?,?,?,?,?,now(),?)";
	@Override
	public int saveDollOptRecord(DollOptRecord record) {
		LOG.info("saveDollOptRecord"+JSON.toJSONString(record));
		return jdbcHandler.executeSql(saveDollOptRecord, record.getUid(), record.getBusId(), record.getLogId(), record.getDollId(),
				record.getCost(), record.getResult());
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
	
	String saveDollAppealRecord = "insert into doll_appeal_record(uid,optId,customerId,groupId,reason,status,createTime,updateTime) values(?,?,?,?,?,0,now(),now())";
	@Override
	public int saveDollAppealRecord(int customerId,int groupId,long optId, Long uid, String reason) {
		return jdbcHandler.executeSql(saveDollAppealRecord, uid, optId,customerId,groupId, reason);
	}

	@Override
	public int saveDollAppealRecord(DollAppealRecord dollAppealRecord) {

		String saveDollAppealRecord = "insert into doll_appeal_record(uid,optId,customerId,groupId,dollId,result,reason,status,createTime,updateTime) values(?,?,?,?,?,?,?,0,now(),now())";


		return jdbcHandler.executeSql(saveDollAppealRecord, dollAppealRecord.getUid(), dollAppealRecord.getOptId(),dollAppealRecord.getCustomerId(),dollAppealRecord.getGroupId(),dollAppealRecord.getDollId(),dollAppealRecord.getResult(), dollAppealRecord.getReason());
	}

	String getSuccOptRecord = "select * from doll_success_opt_record order by createTime desc limit ?";
	@Override
	public List<DollSuccessOptRecord> getSuccOptRecord(int num) {
		return jdbcHandler.queryBySql(DollSuccessOptRecord.class, getSuccOptRecord, num);
	}
	String addWatchRecord = "insert into doll_watch_record(uid,busId,time) values(?,?,now())  on duplicate key update busId=? ,time=now()";
	@Override
	public int addWatchRecord(long uid, int busId) {
		return jdbcHandler.executeSql(addWatchRecord, uid,busId,busId);
	}
	String addApplyLog = "INSERT INTO `doll_apply_log`(`uid`,`busId`,`deviceId`,`res`,`time`)VALUES(?,?,?,?,now());";
	@Override
	public void addApplyLog(long uid, int busId, String deviceId, String res) {
		jdbcHandler.executeSql(addApplyLog, uid,busId,deviceId,res);
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
	
	String saveDollSucOptRecord = "insert into doll_success_opt_record(optId,uid,dollId,busId,createTime,groupId,customerId,agentId) values(?,?,?,?,now(),?,?,?)";
	@Override
	public int saveDollSucOptRecord(long optId, long uid, int dollId,int busId,int groupId,int customerId,long agentId) {
		return jdbcHandler.executeSql(saveDollSucOptRecord, optId, uid, dollId, busId,groupId,customerId,agentId);
	}
	
	String getOptRecordByLogId = "select * from doll_opt_record where logId=?";
	@Override
	public DollOptRecord getOptRecordByLogId(long logId) {
		return jdbcHandler.queryOneBySql(DollOptRecord.class, getOptRecordByLogId, logId);
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
	public int updateWatchBus(long uid,int authType) {
		return jdbcHandler.executeSql("update doll_watch_record set authType=? where uid=?",authType,uid);
	}

	@Override
	public void deleteWatchRecord(long uid, int busId) {
		jdbcHandler.executeSql("delete from doll_watch_record where uid=? and busId=?", uid,busId);
	}
	@Override
	public void addDailyPlayRecord(long uid, int isSuccess) {
		jdbcHandler.executeSql("insert into doll_bus_user_daily_record"
				+ " (uid,dateCol,total,success,time) "
				+ "value (?,DATE_FORMAT(now(),'%Y-%m-%d'),1,?,now()) on duplicate key update total=total+1,success=success+?", uid,isSuccess,isSuccess);
	}
	@Override
	public int getDailyPlayCount(long uid) {
		return jdbcHandler.queryCount("select total from doll_bus_user_daily_record where uid=? and dateCol=DATE_FORMAT(now(),'%Y-%m-%d')", uid);
	}

	String getSuccOptRecordcustomerId = "select * from doll_success_opt_record where customerId=? order by createTime desc limit ?";
	@Override
	public List<DollSuccessOptRecord> getSuccOptRecord(int customerId, int num) {
		return jdbcHandler.queryBySql(DollSuccessOptRecord.class, getSuccOptRecordcustomerId,customerId, num);
	}
	
}
