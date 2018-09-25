package com.imlianai.dollpub.app.modules.support.relation.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.value.UserValueService;
import com.imlianai.dollpub.app.modules.support.relation.cache.RelationCacheService;
import com.imlianai.dollpub.app.modules.support.relation.dao.RelationDao;
import com.imlianai.dollpub.app.modules.support.relation.router.TableRouter;
import com.imlianai.dollpub.domain.relation.RelationRecord;
import com.imlianai.dollpub.domain.relation.RelationRecord.RelationType;
import com.imlianai.rpc.support.common.BaseLogger;

@Service
public class RelationServiceImpl implements RelationService{
	
	private final BaseLogger logger = BaseLogger.getLogger(RelationServiceImpl.class);

	@Resource
	private RelationCacheService relationCacheService;
	@Resource
	private UserValueService userValueService;
	@Resource
	private RelationDao relationDao;
	@Resource
	private TableRouter tableRouter;
//	@Resource
//	private ArticleService articleService;
	@Resource
	private UserService userService;
	
	@Override
	public int addFollow(long uid, long tid) {
		//从缓存中获取两个用户之间的数据
		List<RelationRecord> records = relationCacheService.getRelationRecord(uid, tid);
		logger.info("addFollow uid:" + uid + " tid:" + tid);
		boolean selfFollowFlag = false;
		boolean competitorFlag = false;
		boolean followOnce=false;
		boolean selfBlackOp=false;
		boolean otherBlackOp=false;
		return 1;
		// 获取双方关注信息
		/*if (records != null && !records.isEmpty()) {
			for (RelationRecord record : records) {
				if (record.getUid() == uid && record.getFollowUid() == tid) {
					if (record.getStatus()>=0) {// 己方已关注
						selfFollowFlag = true;
					}else if(record.getStatus()==-1) {//曾经关注
						followOnce = true;
					}else if(record.getStatus()==-2){//已拉黑
						selfBlackOp = true;
					}
				} else if (record.getUid() == tid && record.getFollowUid() == uid) {// 对方已关注
					if (record.getStatus() >= 0){// 对方已关注
						competitorFlag = true;
					}else if(record.getStatus()==-2){//对方拉黑
						otherBlackOp = true;
					}
				}
			}
		}
		if (selfFollowFlag)// 自己已关注，不处理
			return -3;
		if (otherBlackOp)//对方拉黑
			return -4;
		if (selfBlackOp)//已拉黑对方
			return -5;
		if (competitorFlag) {// 对方关注了
			if (relationDao.addFollowRecord(uid, tid, RelationType.Fans.type) > 0) {
				//判断uid 跟 tid 是否在同一张表
				if(!tableRouter.judgeHashvalue(uid, tid)){
					//若不在同一张表 则在tid所在的表中也需要保存相同的一份记录
					relationDao.addFollowRecord(tid, uid, RelationType.Fans.type);
					relationDao.addFollowRecordBySecond(uid, tid, RelationType.Fans.type);
				}
				// 更新对方关注记录
				relationDao.addFollowRecordBySecond(tid, uid, RelationType.Fans.type);
				// 更新数量
				userValueService.updateFollowNum(uid, 1);
				userValueService.updateFanNum(tid, 1);
				//更新好友数量-双方更新
				userValueService.updateFriendNum(uid, 1);
				userValueService.updateFriendNum(tid, 1);
				//更新缓存
				relationCacheService.delRelationRecord(uid, tid);
				relationCacheService.delFollowUids(uid);
				if (followOnce)
					return 3;
				return 2;
			} else {
				return -1;// 添加关注记录失败
			}
		} else {// 对方未关注
			if (relationDao.addFollowRecord(uid, tid, RelationType.Follow.type) > 0) {
				//判断uid 跟 tid 是否在同一张表
				if(!tableRouter.judgeHashvalue(uid, tid)){
					//若不在同一张表 则在tid所在的表中也需要保存相同的一份记录
					relationDao.addFollowRecordBySecond(uid, tid,  RelationType.Follow.type);
				}
				// 更新数量
				userValueService.updateFollowNum(uid, 1);
				userValueService.updateFanNum(tid, 1);
				//更新缓存
				relationCacheService.delRelationRecord(uid, tid);
				relationCacheService.delFollowUids(uid);
				if (followOnce)
					return 3;
				return 1;
			} else {
				return -1;// 添加关注记录失败
			}
		}*/
	
				
	}

	@Override
	public int unFollow(long uid, long tid) {
		boolean selfFollowFlag = false;
		boolean competitorFlag = false;
		return 1;
		/*// 找出双方关注记录
		List<RelationRecord> records = relationCacheService.getRelationRecord(uid, tid);
		if (records != null && !records.isEmpty()) {
			for (RelationRecord record : records) {
				if (record.getUid() == uid && record.getFollowUid() == tid) {// 己方已关注
					if (record.getStatus()>=0) {// 己方已关注
						selfFollowFlag = true;
					}
				} else if (record.getUid() == tid && record.getFollowUid() == uid) {// 对方已关注
					if (record.getStatus()>=0) {// 对方已关注
						competitorFlag = true;
					}
				}
			}
		} else {// 双方均未关注
			return -1;// 添加关注记录失败
		}
		if (selfFollowFlag) {
			if (relationDao.addFollowRecord(uid, tid, RelationType.DelFollow.type) > 0) {
				//判断uid 跟 tid 是否在同一张表
				if(!tableRouter.judgeHashvalue(uid, tid)){
					//若不在同一张表 则在tid所在的表中也需要保存相同的一份记录
					relationDao.addFollowRecordBySecond(uid, tid, RelationType.DelFollow.type);
				}
				//更新粉丝关注数量
				userValueService.updateFollowNum(uid, -1);
				userValueService.updateFanNum(tid, -1);
				relationCacheService.delRelationRecord(uid, tid);
				relationCacheService.delFollowUids(uid);
			} else {
				return -1;// 添加取消关注记录失败
			}
		}
		//对方已关注
		if (competitorFlag){
			if(relationDao.addFollowRecordBySecond(tid, uid, RelationType.Follow.type) > 0){
				//判断uid 跟 tid 是否在同一张表
				if(!tableRouter.judgeHashvalue(uid, tid)){
					//若不在同一张表 则在tid所在的表中也需要保存相同的一份记录
					relationDao.addFollowRecord(tid, uid,  RelationType.Follow.type);
				}
				//更新好友数量-双方更新
				userValueService.updateFriendNum(uid, -1);
				userValueService.updateFriendNum(tid, -1);
			}
		}
		return 1;*/
	}

	@Override
	public List<Long> getFollowUids(long uid) {
		return relationCacheService.getFollowUids(uid);
	}

	@Override
	public List<Long> getFansUids(long uid) {
		List<Long> resList = new ArrayList<Long>();
		List<RelationRecord> list = relationDao.getRelationRecordByType(uid, RelationType.Fans.type, 0, 0);
		if (list != null) {
			for (RelationRecord relationRecord : list) {
				resList.add(relationRecord.getUid());
			}
		}
		return resList;
	}
	
	@Override
	public int getRelation(long uid, long tid) {
		List<RelationRecord> records = relationCacheService.getRelationRecord(uid, tid);
		int relation = 0;
		if(records==null || records.isEmpty())
			return relation;
		for(RelationRecord record : records){
			if(record.getUid() == uid){
				if(record.getStatus() == 0){
					relation = 1;
				}else if(record.getStatus() == 1){
					relation = 2;
				}else if(record.getStatus() == -2){
					if(relation == -2){
						relation = -3;
					}else{
						relation = -1;
					}
				}
			}else{
				if(record.getStatus() == 0){
					relation = 3;
				}else if(record.getStatus() == 1){
					relation = 2;
				}else if(record.getStatus() == -2){
					if(relation == -1){
						relation = -3;
					}else{
						relation = -2;
					}
				}
			}
		}
		return relation;
	}

	@Override
	public List<RelationRecord> getRelationRecord(long uid, int relationType, int page, int limit) {
		return relationDao.getRelationRecordByType(uid, relationType, (page-1)*limit, limit);
	}
	
	@Override
	public int addBlack(long uid, long tid) {
		logger.info("addBlack uid:" + uid + " tid:" + tid);
		boolean selfBlackFlag = false;
		boolean selfFollowFlag = false;
		boolean competitorFlag = false;
		return 1;
		/*
		// 获取双方关注信息
		List<RelationRecord> records = relationCacheService.getRelationRecord(uid, tid);
		if (records != null && !records.isEmpty()) {
			for (RelationRecord record : records) {
				if (record.getUid() == uid && record.getFollowUid() == tid) {
					if (record.getStatus() == -2) {// uid将tid拉黑
						selfBlackFlag = true;
					} else if (record.getStatus()==1 || record.getStatus()==0) {// uid关注tid
						selfFollowFlag = true;
					}
				} else if (record.getUid() == tid && record.getFollowUid() == uid){
					if((record.getStatus() == 1 || record.getStatus() == 0))// tid关注uid
						competitorFlag = true;
				}
			}
		}
		if (selfBlackFlag) {// uid已将tid拉黑，不处理
			return -3;// 不能重复拉黑
		} else {
			//判断uid 跟 tid 是否在同一张表
			if(!tableRouter.judgeHashvalue(uid, tid))
				relationDao.addFollowRecord(uid, tid, RelationType.Black.type);
			relationDao.addFollowRecord(uid, tid, RelationType.Black.type);
			if (selfFollowFlag) {// 自己已关注
				// 对方粉丝数改变
				userValueService.updateFollowNum(uid, -1);
				userValueService.updateFanNum(tid, -1);
			}
			if (competitorFlag) {// 对方有关注时，对方关系改为取消关注
				if(!tableRouter.judgeHashvalue(uid, tid))
					relationDao.addFollowRecordBySecond(tid, uid, RelationType.DelFollow.type);
				relationDao.addFollowRecord(tid, uid, RelationType.DelFollow.type);
				userValueService.updateFollowNum(tid, -1);
				userValueService.updateFanNum(uid, -1);
			}
			relationCacheService.delRelationRecord(uid, tid);
			return 1;
		}*/
	}
	
	@Override
	public int deleteBlack(long uid, long tid) {
		int black = getRelation(uid, tid);
		if (black==-1 || black==-3) {
			if(relationDao.addFollowRecord(uid, tid, RelationType.DelFollow.type) > 0){
				if(!tableRouter.judgeHashvalue(uid, tid))
					relationDao.addFollowRecordBySecond(uid, tid, RelationType.DelFollow.type);
				relationCacheService.delRelationRecord(uid, tid);
			}
			return 1;
		} else {// 没有拉黑
			return -1;
		}
	}
	
	@Override
	public void noticeFan(final long artId, final int type) {
	}
}
