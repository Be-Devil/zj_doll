package com.imlianai.dollpub.app.modules.support.invite.h5.service;

import java.util.List;

import com.imlianai.dollpub.domain.invite.InviteH5Relation;
import com.imlianai.dollpub.domain.invite.InviteH5RewardCatalog;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

/**
 * 邀请相关
 * 
 * @author Max
 * 
 */
public interface InviteH5Service {
	

	/**
	 * 根据uid获取邀请关系
	 * @param uid
	 * @return
	 */
	public InviteH5Relation getInviteRelationByUid(long uid);
	
	/**
	 * 新增邀请
	 * @param uid
	 * @param inviteId
	 * @return
	 */
	public int addInvite(long uid, long inviteId);
	

	/**
	 * 处理成功上机
	 * 
	 * @param uid
	 */
	public void handleApplySuccess(long uid);
	
	/**
	 * 获取任务进度
	 * @param uid
	 * @return
	 */
	public List<InviteH5RewardCatalog> getInviteH5RewardCatalog(long uid);
	
	/**
	 * 领取奖励
	 * @param uid
	 * @param id
	 * @return
	 */
	public BaseRespVO gainInviteH5Reward(long uid,int id);

	/**
	 * 获取邀请任务状态
	 * @param uid
	 * @param id
	 * @return
	 */
	public int getInviteH5RewardState(long uid, int id);
}
