package com.imlianai.zjdoll.app.modules.core.doll.pattern.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollOptRecord.DollOptRecordStrong;
import com.imlianai.zjdoll.domain.doll.pattern.BusPatternStat;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.DollBusStrongDetailRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserSpecialCatchRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserStageRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.UserStageRecord.UserRoundStage;

@Repository
public class DollBusPatternDaoImpl implements DollBusPatternDao {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public BusPatternStat getStat(int busId) {
		return jdbcHandler.queryOneBySql(BusPatternStat.class,
				"select * from doll_bus_pattern_stat where busId=? limit 1",
				busId);
	}

	@Override
	public void initStat(int busId) {
		jdbcHandler
				.executeSql(
						"insert into doll_bus_pattern_stat (busId,time) value (?,now())",
						busId);
	}

	@Override
	public void addStrongRecord(long optId, long logId, long uid, int busId,
			String deviceId, int round, int current, int strong) {
		jdbcHandler.executeSql("insert into doll_bus_strong_record"
				+ "(optId,logId,uid,busId,deviceId,round,current,strong,time) "
				+ "value (?,?,?,?,?,?,?,?,now())", optId, logId, uid, busId,
				deviceId, round, current, strong);
	}

	@Override
	public void updateStat(int busId, int round) {
		if (round == 0) {
			jdbcHandler.executeSql(
					"update doll_bus_pattern_stat set current=current+1,total=total+1,time=now()"
							+ " where busId=?", busId);
		} else {
			jdbcHandler
					.executeSql(
							"update doll_bus_pattern_stat set current=0,total=total+1,round=round+1,time=now()"
									+ " where busId=?", busId);
		}
	}

	@Override
	public void resetStatRound(int busId) {
		jdbcHandler.executeSql(
				"update doll_bus_pattern_stat set round=round+1,current=0,time=now()"
						+ " where busId=?", busId);
	}

	@Override
	public void updateStrongFlag(int busId, int strong) {
		jdbcHandler.executeSql("update doll_bus_pattern_stat set strong=?"
				+ " where busId=?", strong, busId);
	}

	@Override
	public void addDetailRecord(long optId, int busId, long uid,
			int busSettingStrong, int totalStrong, int currentCount, int success) {
		jdbcHandler
				.executeSql(
						"insert into doll_bus_Strong_detail_record (optId,busId,uid,busSettingStrong,totalStrong,currentCount,success,time) value (?,?,?,?,?,?,?,now())",
						optId, busId, uid, busSettingStrong, totalStrong,
						currentCount, success);
	}

	@Override
	public void updateDetailRecord(long optId) {
		jdbcHandler
				.executeSql(
						"update doll_bus_Strong_detail_record set isClose=1,strongSetEndTime=now() where optId=?",
						optId);
	}

	@Override
	public List<DollBusStrongDetailRecord> getDollBusStrongDetailRecord(
			int busId, String start, String end) {
		if (StringUtil.isNullOrEmpty(start)||StringUtil.isNullOrEmpty(end)) {
			if (busId>0) {
				return jdbcHandler.queryBySql(DollBusStrongDetailRecord.class, "select * from doll_bus_Strong_detail_record where busId=? order by id desc " ,busId);
			}else {
				return jdbcHandler.queryBySql(DollBusStrongDetailRecord.class, "select * from doll_bus_Strong_detail_record  order by id desc " );
			}
		}else {
			if (busId>0) {
				return jdbcHandler.queryBySql(DollBusStrongDetailRecord.class, "select * from doll_bus_Strong_detail_record where busId=? and time>=? and time<=? order by id desc " ,busId,start,end);
			}else {
				return jdbcHandler.queryBySql(DollBusStrongDetailRecord.class, "select * from doll_bus_Strong_detail_record where time>=? and time<=? order by id desc  " ,start,end);
			}
		}
		
	}

	@Override
	public void initUserStrongRecord(long uid, int successExpect) {
		jdbcHandler.executeSql("insert into user_special_catch_record (uid,successExpect,time) value (?,?,now()) on duplicate key update successExpect=successExpect+?,time=now()", uid,successExpect,successExpect);
	}

	@Override
	public void updateStrongCount(long uid, long optId) {
		jdbcHandler.executeSql("update user_special_catch_record set strongCount=strongCount+1,lastOptId=?,totalCatch=totalCatch+1 where uid=? limit 1", optId,uid);
	}

	@Override
	public void updateCatchCount(long uid) {
		jdbcHandler.executeSql("update user_special_catch_record set totalCatch=totalCatch+1 where uid=? limit 1", uid);
	}
	
	@Override
	public int hasStrongChange(long uid) {
		return jdbcHandler.queryCount("select successExpect from user_special_catch_record where uid=? limit 1", uid);
	}
	
	@Override
	public int comsumeStrongChange(long uid,int change) {
		return jdbcHandler.executeSql("update user_special_catch_record set successExpect=successExpect-?,strongSuccess=strongSuccess+1 where uid=? and successExpect-?>=0 limit 1", change,uid,change);
	}
	
	@Override
	public UserSpecialCatchRecord getUserSpecialCatchRecord(long uid) {
		return jdbcHandler.queryOneBySql(UserSpecialCatchRecord.class,"select * from user_special_catch_record where uid=? limit 1", uid);
	}
	
	
	@Override
	public BusPatternStat getStatZengjing(int busId) {
		return jdbcHandler.queryOneBySql(BusPatternStat.class,
				"select * from doll_bus_pattern_stat_zengjing where busId=? limit 1",
				busId);
	}

	@Override
	public void initStatZengjing(int busId) {
		jdbcHandler
				.executeSql(
						"insert into doll_bus_pattern_stat_zengjing (busId,time) value (?,now())",
						busId);
	}

	@Override
	public void updateStatZengjing(int busId, int round) {
		if (round == 0) {
			jdbcHandler.executeSql(
					"update doll_bus_pattern_stat_zengjing set current=current+1,total=total+1,time=now()"
							+ " where busId=?", busId);
		} else {
			jdbcHandler
					.executeSql(
							"update doll_bus_pattern_stat_zengjing set current=0,total=total+1,round=round+1,time=now()"
									+ " where busId=?", busId);
		}
	}

	@Override
	public void resetStatRoundZengjing(int busId) {
		jdbcHandler.executeSql(
				"update doll_bus_pattern_stat_zengjing set round=round+1,current=0,time=now()"
						+ " where busId=?", busId);
	}

	@Override
	public void updateStrongFlagZengjing(int busId, int strong) {
		jdbcHandler.executeSql("update doll_bus_pattern_stat_zengjing set strong=?"
				+ " where busId=?", strong, busId);
	}

	@Override
	public int getCatchUserSetting(long uid) {
		return jdbcHandler.queryCount("select value from user_catch_temp_setting where uid=? limit 1", uid);
	}

	@Override
	public UserStageRecord getUserStage(long uid) {
		return jdbcHandler.queryOneBySql(UserStageRecord.class, "select * from user_stage_record where uid=?",uid);
	}

	@Override
	public void initUserStage(long uid) {
		jdbcHandler.executeSql("insert into user_stage_record(uid,time)value(?,now()) on duplicate key update time=now()", uid);
	}

	@Override
	public void updateUserStage(long uid, long optId, boolean isStrong) {
		jdbcHandler.executeSql("update user_stage_record "
				+ "set total=total+1,num=num+1,time=now(),lastOptId=?,strongTotal=strongTotal+?"
				+ " where uid=?", optId,isStrong?1:0,uid);
	}

	@Override
	public void updateUserStage(long uid, int stage) {
		if (stage==UserRoundStage.BeWeak.type) {
			jdbcHandler.executeSql("update user_stage_record set stage=?,num=0,round=round+1 where uid=?", stage,uid);
		}else{
			jdbcHandler.executeSql("update user_stage_record set stage=?,num=0 where uid=?", stage,uid);
		}
	}

	@Override
	public void updateUserStageSuccess(long uid, boolean isStrong) {
		if (isStrong){
			jdbcHandler.executeSql("update user_stage_record set strongSuccess=strongSuccess+1 where uid=?", uid);
		}else {
			jdbcHandler.executeSql("update user_stage_record set normalSuccess=normalSuccess+1 where uid=?", uid);
		}
	}
	
}
