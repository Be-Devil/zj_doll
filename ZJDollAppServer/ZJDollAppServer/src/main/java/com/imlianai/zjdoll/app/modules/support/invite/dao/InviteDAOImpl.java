package com.imlianai.zjdoll.app.modules.support.invite.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.constants.CacheConst;
import com.imlianai.zjdoll.constants.DollCacheConst;
import com.imlianai.zjdoll.domain.invite.InvitePreRecord;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.invite.InviteRewardRecord;
import com.imlianai.zjdoll.domain.invite.OfficialCode;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipe;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipes;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;
import com.imlianai.zjdoll.app.modules.support.invite.util.InviteUtil;

@Repository
public class InviteDAOImpl implements InviteDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	private String getCode = "select code from invite_code_pool where code>? limit 1";
	private String getCodeDel = "delete from invite_code_pool where code=? ";

	@Override
	public long getInviteCode(long location) {
		Long code = jdbcHandler.queryOneBySql(Long.class, getCode, location);
		if (code == null) {
			return 0;
		}
		if (code > 0) {
			int flag = jdbcHandler.executeSql(getCodeDel, code);
			if (flag > 0) {
				return code;
			}
		}
		return 0;
	}

	private String initInviteCode = "insert into invite_relation(uid,code,time) values(?,?,now()) ";

	@Override
	@CacheWipe(key = CacheConst.INVITE_INFO_CACHE, pkField = "uid")
	public int initInviteCode(long uid, long code) {
		return jdbcHandler.executeSql(initInviteCode, uid, code);
	}

	private String addInvite = "update invite_relation set inviteUid=?,time=now() where uid=? and inviteUid=0 ";

	@CacheWipe(key = CacheConst.INVITE_INFO_CACHE, pkField = "uid")
	@Override
	public int addInvite(long uid, long inviteId) {
		return jdbcHandler.executeSql(addInvite, inviteId, uid);
	}

	private String getInviteId = "select inviteUid from invite_relation where uid=? ";

	@CacheWrite(key = CacheConst.INVITE_INFO_CACHE, pkField = "uid", validTime = 86400)
	@Override
	public long getInviteId(long uid) {
		Long inviteId = jdbcHandler.queryOneBySql(Long.class, getInviteId, uid);
		if (inviteId == null)
			inviteId = 0L;
		return inviteId;
	}

	@Override
	public int hasGainRegReward(String imei) {
		return jdbcHandler.queryCount(
				"select 1 from invite_user_reg_record where imei=? limit 1",
				imei);
	}

	@Override
	public int addGainRegReward(long uid, String imei) {
		return jdbcHandler
				.executeSql(
						"insert into invite_user_reg_record (uid,imei,time) value (?,?,now())",
						uid, imei);
	}

	@Override
	public int addRewardRecord(InviteRewardRecord record) {
		return jdbcHandler
				.executeSql(
						"insert into invite_reward_record (uid,inviteUid,reward,type,state,time,remark) value (?,?,?,?,?,now(),?)",
						record.getUid(), record.getInviteUid(),
						record.getReward(), record.getType(),
						record.getState(), record.getRemark());
	}

	@Override
	public int updateRewardRecord(long id, int state) {
		return jdbcHandler
				.executeSql(
						"update invite_reward_record set state=? where id=? and state<>?",
						state, id, state);
	}

	@Override
	public InviteRelation getInviteRelationByCode(long code) {
		return jdbcHandler.queryOneBySql(InviteRelation.class,
				"select * from invite_relation where code=? limit 1", code);
	}

	@Override
	public InviteRelation getInviteRelationByUid(long uid) {
		return jdbcHandler.queryOneBySql(InviteRelation.class,
				"select * from invite_relation where uid=? limit 1", uid);
	}

	@Override
	public List<InviteRelation> getInviteRelationList(long inviteUid) {
		return jdbcHandler.queryBySql(InviteRelation.class,"select * from invite_relation where inviteUid=?",inviteUid);
	}

	@Override
	public int updateInviteUsedTimes(long uid) {

		if (Event20180320InviteUtil.eventStatus() == 1){
				return jdbcHandler
				.executeSql(
						"update invite_relation set usedTimes=usedTimes+1 where uid=? and usedTimes+1<=? ",
						uid, Event20180320InviteUtil.getInviteTimeLimit());
		}

		return jdbcHandler
				.executeSql(
						"update invite_relation set usedTimes=usedTimes+1 where uid=? and usedTimes+1<=? ",
						uid, InviteUtil.getInviteTimeLimit());
	}

	@Override
	public List<InviteRewardRecord> getInviteRewardRecords(long uid, int type,
			int page, int pageSize) {
		return jdbcHandler
				.queryBySql(
						InviteRewardRecord.class,
						"select * from invite_reward_record where inviteUid=? and type=? order by time desc limit ?, ?",
						uid, type, (page - 1) * pageSize, pageSize);
	}

	@Override
	public InviteRewardRecord getInviteRewardRecord(long id) {
		return jdbcHandler.queryOneBySql(InviteRewardRecord.class,
				"select * from invite_reward_record where id=? limit 1", id);
	}

	@Override
	public InviteRewardRecord getInviteRewardRecord(long uid, int type) {
		return jdbcHandler
				.queryOneBySql(
						InviteRewardRecord.class,
						"select * from invite_reward_record where uid=? and type=? limit 1",
						uid, type);
	}

	@Override
	@CacheWrite(key = DollCacheConst.INVITE_OFFICAL_CODE, pkField = "code", validTime = 10)
	public OfficialCode getOfficialCode(long code) {
		return jdbcHandler
				.queryOneBySql(
						OfficialCode.class,
						"select * from official_code where code=? and start<=now() and end>=now() order by id asc",
						code);
	}

	@Override
	@CacheWipe(key = DollCacheConst.INVITE_OFFICAL_CODE, pkField = { "code" })
	public int consumeOfficialCode(int id, long code) {
		return jdbcHandler
				.executeSql(
						"update official_code set consumeNum=consumeNum+1  where id=? and consumeNum+1<=number",
						id);
	}

	@Override
	@CacheWrite(key = "GAIN:OFFICAL:CODE:UID", pkField = { "id", "uid" }, validTime = 10)
	public int hasGainOfficialCode(long uid, int id) {
		return jdbcHandler
				.queryCount(
						"select 1 from invite_official_code_record where uid=? and codeId=? limit 1",
						uid, id);
	}

	@Override
	@CacheWrite(key = "GAIN:OFFICAL:CODE:IMEI", pkField = { "id", "imei" }, validTime = 10)
	public int hasGainOfficialCode(String imei, int id) {
		return jdbcHandler
				.queryCount(
						"select 1 from invite_official_code_record where imei=? and codeId=? limit 1",
						imei, id);
	}

	@Override
	@CacheWipes({
			@CacheWipe(key = "GAIN:OFFICAL:CODE:IMEI", pkField = { "id", "imei" }),
			@CacheWipe(key = "GAIN:OFFICAL:CODE:UID", pkField = { "id", "uid" }) })
	public void addGainOfficialRecord(long uid, String imei, int id, long code,
			int coin) {
		jdbcHandler
				.executeSql(
						"insert into invite_official_code_record (uid,imei,codeId,code,time,coin,dateMark) value (?,?,?,?,now(),?,DATE_FORMAT(now(),'%Y-%m-%d'))",
						uid, imei, id, code, coin);
	}

	@Override
	public void updateOfficialCodeCoin(int id, int coin) {
		jdbcHandler.executeSql(
				"update official_code set coin =coin+? where id=? limit 1",
				coin, id);
	}

	@Override
	public void addInviteTotalRecord(InviteRewardRecord record) {
		jdbcHandler
				.executeSql(
								"insert into invite_reward_record_show (uid,inviteUid,reward,type,state,time,remark) value (?,?,?,?,?,now(),?)",
						record.getUid(), record.getInviteUid(),
						record.getReward(), record.getType(),
						record.getState(), record.getRemark());
	}

	@Override
	public List<InviteRewardRecord> getInviteRewardRecords(int type, int page,
			int pageSize) {
		return jdbcHandler
				.queryBySql(
						InviteRewardRecord.class,
						"select * from invite_reward_record_show where type=? order by id desc limit ?,?",
						type, (page - 1) * pageSize, pageSize);
	}

	@Override
	public int addInviteCodeWrongTimes(long uid) {
		return jdbcHandler.executeSql("insert into invite_code_wrong_record (uid,datecol,num,time) value (?,DATE_FORMAT(now(),'%Y-%m-%d'),1,now()) on duplicate key update num=num+1", uid);
	}

	@Override
	public int getInviteCodeWrongTimes(long uid) {
		return jdbcHandler.queryCount("select num from invite_code_wrong_record where uid=? and datecol=DATE_FORMAT(now(),'%Y-%m-%d') limit 1", uid);
	}
	
	String addPreInviteRecord = "insert into invite_pre_record(tid,unionId,time) values (?,?,now()) on duplicate key update time=now(),tid=?";
	@Override
	public int addPreInviteRecord(long tid, String unionId) {
		return jdbcHandler.executeSql(addPreInviteRecord, tid, unionId, tid);
	}
	
	String updatePreInviteRecord = "update invite_pre_record set regUid=?, regState=1, regTime=now() where unionId=? and regUid=0";
	@Override
	public int updatePreInviteRecord(String unionId, long regUid) {
		return jdbcHandler.executeSql(updatePreInviteRecord, regUid, unionId);
	}

	String getInvitePreRecordList = "select * from invite_pre_record where tid=? and regState=? order by regTime asc limit ?";
	@Override
	public List<InvitePreRecord> getInvitePreRecordList(Long uid, int regState, int size) {
		return jdbcHandler.queryBySql(InvitePreRecord.class, getInvitePreRecordList, uid, regState, size);
	}

	String getInvitePreRecordByUnionId = "select * from invite_pre_record where unionId=? limit 1";
	@Override
	public InvitePreRecord getInvitePreRecordByUnionId(String unionId) {
		return jdbcHandler.queryOneBySql(InvitePreRecord.class, getInvitePreRecordByUnionId, unionId);
	}

	String getInvitePreRecordByUid = "select count(1) from invite_pre_record where tid=? and regState=?";
	@Override
	public int getInvitePreRecordByUid(Long uid, int regState) {
		return jdbcHandler.queryCount(getInvitePreRecordByUid, uid, regState);
	}

}
