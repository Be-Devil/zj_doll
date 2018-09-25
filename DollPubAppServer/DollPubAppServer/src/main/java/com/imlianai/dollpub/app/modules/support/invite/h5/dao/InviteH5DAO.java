package com.imlianai.dollpub.app.modules.support.invite.h5.dao;

import java.util.List;

import com.imlianai.dollpub.domain.invite.InviteH5Relation;
import com.imlianai.dollpub.domain.invite.InviteH5RewardCatalog;

/**
 * 邀请相关
 * @author Max
 *
 */
public interface InviteH5DAO {

	/**
	 * 新增邀请
	 * @param uid
	 * @param inviteId
	 * @return
	 */
	public int addInvite(long uid, long inviteId);
	
	/**
	 * 获取邀请自己的记录
	 * @param uid
	 * @return
	 */
	public InviteH5Relation getInviteRelation(long uid);

	/**
	 * 更新游戏状态
	 * @param uid
	 * @param gameState
	 * @return
	 */
	public int updateInviteRelationGameState(long uid, int gameState);
	
	/**
	 * 增加邀请人数
	 * @param uid
	 * @param num
	 * @return
	 */
	public int incInviteNum(long uid,int num);
	
	/**
	 * 获取邀请奖励任务内容
	 * @return
	 */
	public List<InviteH5RewardCatalog> getInviteH5RewardCatalog();
	
	/**
	 * 获取邀请奖励任务内容
	 * @param id
	 * @return
	 */
	public InviteH5RewardCatalog getInviteH5RewardCatalog(int id);

	/**
	 * 获取邀请任务状态
	 * @param uid
	 * @param id
	 * @return
	 */
	public int getInviteH5RewardState(long uid,int id);
	
	/**
	 * 增加领取记录
	 * @param uid
	 * @param id
	 * @param state
	 * @param target
	 * @param reward
	 * @param remark
	 * @return
	 */
	public int addInviteH5RewardGainRecord(long uid,int id,int state,int target,int reward,String remark);
	
}
