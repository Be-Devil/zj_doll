package com.imlianai.dollpub.app.modules.support.relation.dao;

import java.util.List;

import com.imlianai.dollpub.domain.relation.RelationRecord;

/**
 * 用户关系
 * @author Max
 *
 */
public interface RelationDao {
	
	/**
	 * 插入关系记录
	 * @param uid
	 * @param tid
	 * @param status 0 关注 1 好友 -1 取消关注 -2拉黑
	 * @return
	 */
	public int addFollowRecord(long uid, long tid, int status);
	
	/**
	 * 插入关注记录 表名由tid决定
	 * @param uid
	 * @param tid
	 * @param status 0 关注 1 好友 -1 取消关注 -2拉黑
	 * @return
	 */
	public int addFollowRecordBySecond(long uid, long tid, int status);
	
	/**
	 * 获取双方关注关系
	 * @param uid
	 * @param tid
	 * @return
	 */
	public List<RelationRecord> getRelationRecord(long uid, long tid);
	
	/**
	 * 根据类型获取某用户的关系列表
	 * @param uid
	 * @param type
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<RelationRecord> getRelationRecordByType(long uid, int type, int startIndex, int pageSize);

}
