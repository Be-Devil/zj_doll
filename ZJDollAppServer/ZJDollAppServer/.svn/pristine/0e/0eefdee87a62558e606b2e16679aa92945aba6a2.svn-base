package com.imlianai.zjdoll.app.modules.core.egg.dao;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.domain.egg.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class EggMachineDaoImpl implements EggMachineDao {

	@Resource
	JdbcHandler jdbcHandler;

	private String getEggMacClassifies = "select * from egg_machine_classify where valid=1 and endTime>now() and startTime<now() order by `sortIndex` asc";
	@Override
	public List<EggMacClassifyInfo> getEggMacClassifies() {
		return jdbcHandler.queryBySql(EggMacClassifyInfo.class, getEggMacClassifies);
	}
	
	private String getEggMachines = "select * from egg_machine where valid=1 order by `sortIndex` asc";
	@Override
	public List<EggMachine> getEggMachines() {
		return jdbcHandler.queryBySql(EggMachine.class, getEggMachines);
	}
	
	private String getEggMachineClassifyByType = "select * from egg_machine_classify where type=? and valid=1 and endTime>now() and startTime<now() limit 1";
	@Override
	public List<Long> getMachineIdsByType(int type) {
		EggMachineClassify eggMachineClassify = jdbcHandler.queryOneBySql(EggMachineClassify.class, getEggMachineClassifyByType, type);
		if (eggMachineClassify != null) {
			String machineIdsStr = eggMachineClassify.getMachineIds();
			if (machineIdsStr != null) {
				try {
					List<Long> machineIds = JSON.parseArray(machineIdsStr,
							Long.class);
					return machineIds;
				} catch (Exception e) {

				}
			}
		}
		return null;
	}
	
	private String getRewardConfsByMachineId = "select * from egg_machine_reward_conf where machineId=? order by sortIndex asc";
	@Override
	public List<EggMachineRewardConf> getRewardConfsByMachineId(long machineId) {
		return jdbcHandler.queryBySql(EggMachineRewardConf.class, getRewardConfsByMachineId, machineId);
	}
	
	private String getAwardNum = "select num from egg_machine_award_num where dollId=?";
	@Override
	public int getAwardNum(int dollId) {
		return jdbcHandler.queryCount(getAwardNum, dollId);
	}
	
	private String getRecentlyUserPlayRecords = "select * from egg_machine_user_play_record where status=1 and machineId=? order by createTime desc limit ?";
	@Override
	public List<EggMachineUserPlayRecord> getRecentlyUserPlayRecords(int size, long machineId) {
		return jdbcHandler.queryBySql(EggMachineUserPlayRecord.class, getRecentlyUserPlayRecords, machineId, size);
	}
	
	private String getUserAccount = "select * from egg_machine_user_account where uid=?";
	@Override
	public EggMachineUserAccount getUserAccount(Long uid) {
		return jdbcHandler.queryOneBySql(EggMachineUserAccount.class, getUserAccount, uid);
	}
	
	private String updateUserTimeCoupon = "update egg_machine_user_account set num=num+? where uid=? and num+?>=0";
	@Override
	public int updateUserTimeCoupon(Long uid, int num) {
		return jdbcHandler.executeSql(updateUserTimeCoupon, num, uid, num);
	}
	
	private String updateUserBalance = "update egg_machine_user_account set amount=amount+? where uid=? and amount+?>=0";
	@Override
	public int updateUserBalance(Long uid, int amount) {
		return jdbcHandler.executeSql(updateUserBalance, amount, uid, amount);
	}
	
	private String updateAwardNum = "insert into egg_machine_award_num(dollId,num,createTime,updateTime) values(?,?,now(),now()) on duplicate key update num=num+?,updateTime=now()";
	@Override
	public int updateAwardNum(int dollId, int num) {
		return jdbcHandler.executeSql(updateAwardNum, dollId, num, num);
	}
	
	private String saveUserOptRecord = "insert into egg_machine_user_opt_record(uid,dateCode,type,remark,machineId,time,createTime,updateTime) values(?,?,?,?,?,?,now(),now())";
	@Override
	public int saveUserOptRecord(Long uid, int dateCode, int type, Long machineId, String remark, int time) {
		return jdbcHandler.executeSql(saveUserOptRecord, uid, dateCode, type, remark, machineId, time);
	}
	
	private String saveUserPlayRecord = "insert into egg_machine_user_play_record(optId,uid,dateCode,dollId,machineId,createTime,updateTime) values(?,?,?,?,?,now(),now())";
	@Override
	public int saveUserPlayRecord(long optId, Long uid, int dateCode, int dollId, Long machineId) {
		return jdbcHandler.executeSql(saveUserPlayRecord, optId, uid, dateCode, dollId, machineId);
	}
	
	private String getUserOptRecordByOptId = "select * from egg_machine_user_opt_record where id=?";
	@Override
	public EggMachineUserOptRecord getUserOptRecordByOptId(Long optId) {
		return jdbcHandler.queryOneBySql(EggMachineUserOptRecord.class, getUserOptRecordByOptId, optId);
	}
	
	private String getOvertimeUserOptRecords = "select * from egg_machine_user_opt_record where status=0 and createTime<DATE_SUB(now(), INTERVAL ? SECOND)";
	@Override
	public List<EggMachineUserOptRecord> getOvertimeUserOptRecords(int seconds) {
		return jdbcHandler.queryBySql(EggMachineUserOptRecord.class, getOvertimeUserOptRecords, seconds);
	}
	
	private String updateUserOptRecord = "update egg_machine_user_opt_record set status=?,updateTime=now() where status=? and id=?";
	@Override
	public int updateUserOptRecord(Long id, int originStatus, int endStatus) {
		return jdbcHandler.executeSql(updateUserOptRecord, endStatus, originStatus, id);
	}
	
	private String getUserPlayRecordsByOptId = "select * from egg_machine_user_play_record where optId=?";
	@Override
	public List<EggMachineUserPlayRecord> getUserPlayRecordsByOptId(Long optId) {
		return jdbcHandler.queryBySql(EggMachineUserPlayRecord.class, getUserPlayRecordsByOptId, optId);
	}
	
	private String updateUserPlayRecord = "update egg_machine_user_play_record set status=?,updateTime=now() where status=? and id=?";
	@Override
	public int updateUserPlayRecord(Long id, int originStatus, int endStatus) {
		return jdbcHandler.executeSql(updateUserPlayRecord, endStatus, originStatus, id);
	}
	
	private String updateEggMachineStatus = "update egg_machine set valid=? where machineId=?";
	@Override
	public int updateEggMachineStatus(Long machineId, int status) {
		return jdbcHandler.executeSql(updateEggMachineStatus, status, machineId);
	}
	
	private String updateUserPlayRecordOfUDollId = "update egg_machine_user_play_record set uDollId=?,updateTime=now() where id=?";
	@Override
	public int updateUserPlayRecord(long uDollId, Long id) {
		return jdbcHandler.executeSql(updateUserPlayRecordOfUDollId, uDollId, id);
	}
	
}
