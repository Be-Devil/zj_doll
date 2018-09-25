package com.imlianai.zjdoll.app.modules.support.invite.service;

import java.util.List;

import com.imlianai.zjdoll.domain.invite.InvitePreRecord;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.invite.InviteRewardRecord;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

/**
 * 邀请相关
 * 
 * @author Max
 * 
 */
public interface InviteService {
	
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
	 * 新增邀请
	 * 
	 * @param uid
	 * @param inviteId
	 * @return
	 */
	public BaseRespVO addInvite(long uid, long inviteId,long code);

	/**
	 * 处理成功捉取娃娃
	 * 
	 * @param uid
	 */
	public void handleCatchDollSuccess(long uid);

	/**
	 * 领取邀请奖励
	 * 
	 * @param uid
	 * @param rewardId
	 * @return
	 */
	public BaseRespVO gainInviteReward(long uid, long rewardId);

	/**
	 * 获取被邀请记录
	 * 
	 * @param uid
	 * @return
	 */
	public long getInviteId(long uid);


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
	 * 处理注册奖励
	 * @param user
	 * @param base
	 * @param reqVo 
	 */
	public int handleRegReward(UserGeneral user, UserBase base, BaseReqVO reqVo);

	/**
	 * 使用官方邀请码
	 * @param uid
	 * @param code
	 * @return
	 */
	public BaseRespVO addOfficalCode(long uid, String imei,long code);
	
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
	 */
	int addPreInviteRecord(long tid, String unionId);
	
	/**
	 * 更新预注册记录
	 * @param unionId
	 * @param regUid
	 */
	public int updatePreInviteRecord(String unionId,long regUid);

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
	 */
	public InvitePreRecord getInvitePreRecordByUnionId(String unionId);

	/**
	 * 获取邀请人数
	 * @param uid
	 * @param regState
	 * @return
	 */
	public int getInvitePreRecordByUid(Long uid, int regState);
	
	/**
	 * 更新邀请者渠道枢纽
	 * @param uid 
	 * @param sourceUid 上级邀请用户
	 */
	public void handleUserParent(long uid,long sourceUid);
}
