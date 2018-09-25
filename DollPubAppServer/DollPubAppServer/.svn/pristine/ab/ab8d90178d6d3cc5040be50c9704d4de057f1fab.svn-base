package com.imlianai.dollpub.app.modules.support.relation.cache;

import java.util.List;

import com.imlianai.dollpub.domain.relation.RelationRecord;

/**
 * 用户关系缓存
 * @author wangzh
 *
 */
public interface RelationCacheService {

	/**
	 * 获取双方关注关系
	 * @param uid
	 * @param tid
	 * @return
	 */ 
	public List<RelationRecord> getRelationRecord(long uid, long tid);
	
	/**
	 * 更新获取双方关注关系
	 * @param uid
	 * @param tid
	 * @return
	 */
	public List<RelationRecord> updateRelationRecord(long uid, long tid);
	
	/**
	 * 删除双方关注关系
	 * @param uid
	 * @param tid
	 */
	public void delRelationRecord(long uid, long tid);
	
	/**
	 * 获取当前uid关注的用户列表
	 * @param uid
	 * @return
	 */
	public List<Long> getFollowUids(long uid);
	
	/**
	 * 更新当前uid关注的用户列表
	 * @param uid
	 * @return
	 */
	public List<Long> updateFollowUids(long uid);
	
	/**
	 * 删除当前uid关注的用户列表
	 * @param uid
	 */
	public void delFollowUids(long uid);
}
