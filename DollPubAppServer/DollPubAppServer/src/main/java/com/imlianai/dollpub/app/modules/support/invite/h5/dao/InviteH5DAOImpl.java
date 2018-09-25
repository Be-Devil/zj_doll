package com.imlianai.dollpub.app.modules.support.invite.h5.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.invite.InviteH5Relation;
import com.imlianai.dollpub.domain.invite.InviteH5RewardCatalog;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class InviteH5DAOImpl implements InviteH5DAO {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public int addInvite(long uid, long inviteId) {
		return jdbcHandler.executeSql("insert into invite_h5_record (uid,inviteUid,gameState,time) value (?,?,0,now())", uid,inviteId);
	}

	@Override
	public InviteH5Relation getInviteRelation(long uid){
		return jdbcHandler.queryOneBySql(InviteH5Relation.class, "select * from invite_h5_record where uid=?",uid);
	}
	
	@Override
	public int updateInviteRelationGameState(long uid,int gameState){
		return jdbcHandler.executeSql("update invite_h5_record set gameState=? where uid=? and gameState<>?", gameState,uid,gameState);
	}

	@Override
	public int incInviteNum(long uid, int num) {
		return jdbcHandler.executeSql("insert into invite_h5_record (uid,time,inviteNum) value (?,now(),?)  on duplicate key update inviteNum=inviteNum+?",uid, num, num);
	}

	@Override
	public List<InviteH5RewardCatalog> getInviteH5RewardCatalog() {
		return jdbcHandler.queryBySql(InviteH5RewardCatalog.class, "select * from invite_h5_reward_catalog order by id asc ");
	}
	
	@Override
	public int getInviteH5RewardState(long uid,int id){
		return jdbcHandler.queryCount("select state from invite_h5_reward_process where id=? and uid=? limit 1", id,uid);
	}
	
	@Override
	public int addInviteH5RewardGainRecord(long uid,int id,int state,int target,int reward,String remark){
		return jdbcHandler.executeSql("insert into invite_h5_reward_process (id,uid,state,target,reward,remark,time) value (?,?,?,?,?,?,now())", id,uid,state,target,reward,remark);
	}

	@Override
	public InviteH5RewardCatalog getInviteH5RewardCatalog(int id) {
		return jdbcHandler.queryOneBySql(InviteH5RewardCatalog.class, "select * from invite_h5_reward_catalog where id=? order by id asc ",id);
	}
}
