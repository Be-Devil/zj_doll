package com.imlianai.zjdoll.app.modules.support.invite.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.invite.InvitePreRecord;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.invite.InviteRewardRecord;
import com.imlianai.zjdoll.domain.invite.OfficialCode;

/**
 * 邀请相关
 * @author Max
 *
 */
public interface InviteDAO {

	/**
	 * 初始化邀请码
	 * @param uid
	 * @param code
	 * @return
	 */
	public int initInviteCode(long uid,long code);
	/**
	 * 新增邀请
	 * @param uid
	 * @param inviteId
	 * @return
	 */
	public int addInvite(long uid, long inviteId);
	
	/**
	 * 获取被邀请记录
	 * @param uid
	 * @return
	 */
	public long getInviteId(long uid);
	
	/**
	 * 根据code获取邀请关系
	 * @param code
	 * @return
	 */
	public InviteRelation getInviteRelationByCode(long code);

	/**
	 * 根据uid获取邀请关系
	 * @param uid
	 * @return
	 */
	public InviteRelation getInviteRelationByUid(long uid);


	/**
	 * 获取该用户下的所有代理用户
	 * @param inviteUid
	 * @return
	 */
	public List<InviteRelation> getInviteRelationList(long inviteUid);

	/**
	 * 插入邀请奖励记录
	 * @param record
	 * @return
	 */
	public int addRewardRecord(InviteRewardRecord record);
	
	/**
	 * 是否已领取注册奖励
	 * @param imei
	 * @return
	 */
	public int hasGainRegReward(String imei);
	
	/**
	 * 增加领取注册奖励记录
	 * @param uid
	 * @param imei
	 * @return
	 */
	public int addGainRegReward(long uid,String imei);
	
	/**
	 * 更新奖励记录
	 * @param id
	 * @param state
	 * @return
	 */
	public int updateRewardRecord(long id,int state);
	
	/**
	 * 更新邀请码使用次数
	 * @param code
	 * @return
	 */
	public int updateInviteUsedTimes(long uid);
	
	/**
	 * 获取邀请码
	 * @param location
	 * @return
	 */
	public long getInviteCode(long location);
	
	/**
	 * 获取邀请奖励
	 * 
	 * @param uid
	 * @param type
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<InviteRewardRecord> getInviteRewardRecords(long uid, int type,
			int page, int pageSize);
	
	/**
	 * 根据奖励id获取奖励记录
	 * @param id
	 * @return
	 */
	public InviteRewardRecord getInviteRewardRecord(long id);
	
	/**
	 * 根据奖励id获取奖励记录
	 * @param uid
	 * @param type
	 * @return
	 */
	public InviteRewardRecord getInviteRewardRecord(long uid,int type);
	
	/**
	 * 获取官方邀请码
	 * @param code
	 * @return
	 */
	public OfficialCode getOfficialCode(long code);
	
	/**
	 * 消耗总数量
	 * @param id
	 * @return
	 */
	public int consumeOfficialCode(int id,long code);
	
	/**
	 * 更新领取到的币数
	 * @param id
	 * @param coin
	 */
	public void updateOfficialCodeCoin(int id,int coin);
	
	/**
	 * 是否领取过
	 * @param uid
	 * @param id
	 * @return
	 */
	public int hasGainOfficialCode(long uid,int id);
	
	/**
	 * 是否领取过
	 * @param uid
	 * @param id
	 * @return
	 */
	public int hasGainOfficialCode(String imei,int id);
	
	/**
	 * 增加领取记录
	 * @param uid
	 * @param imei
	 * @param id
	 * @param code
	 */
	public void addGainOfficialRecord(long uid,String imei,int id,long code,int coin);
	
	/**
	 * 记录用于展示的流水
	 * @param record
	 */
	public void addInviteTotalRecord(InviteRewardRecord record);
	
	/**
	 * 获取邀请奖励滚动列表
	 * @param type
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<InviteRewardRecord> getInviteRewardRecords(int type,
			int page, int pageSize);
	
	/**
	 * 插入输错码次数
	 * @param uid
	 * @return
	 */
	public int addInviteCodeWrongTimes(long uid);
	
	/**
	 * 获取输错码次数
	 * @param uid
	 * @return
	 */
	public int getInviteCodeWrongTimes(long uid);
	
	/**
	 * 保存预注册记录
	 * @param tid
	 * @param unionId
	 */
	int addPreInviteRecord(long tid, String unionId);

	/**
	 * 更新预注册记录
	 * @param unionId
	 * @param regUid
	 * @return
	 */
	int updatePreInviteRecord(String unionId, long regUid);
	
	/**
	 * 获取预注册列表
	 * @param uid
	 * @param regState
	 * @param size 
	 * @return
	 */
	public List<InvitePreRecord> getInvitePreRecordList(Long uid, int regState, int size);
	
	/**
	 * 获取预注册记录
	 * @param unionId
	 * @return
	 */
	public InvitePreRecord getInvitePreRecordByUnionId(String unionId);
	
	/**
	 * 获取邀请人数
	 * @param uid
	 * @param regState
	 * @return
	 */
	public int getInvitePreRecordByUid(Long uid, int regState);
}
