package com.imlianai.dollpub.app.modules.support.invite.service;

import java.util.List;

import com.imlianai.dollpub.domain.invite.InviteRelation;
import com.imlianai.dollpub.domain.invite.InviteRewardRecord;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.dollpub.domain.user.UserSimple;
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
	 */
	public int handleRegReward(UserGeneral user, UserBase base);

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
}
