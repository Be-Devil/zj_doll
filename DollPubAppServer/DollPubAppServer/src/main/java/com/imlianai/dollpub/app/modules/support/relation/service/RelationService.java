package com.imlianai.dollpub.app.modules.support.relation.service;

import java.util.List;

import com.imlianai.dollpub.domain.relation.RelationRecord;

/**
 * 用户关系
 * @author wangzh
 *
 */
public interface RelationService {

	/**
	 * 添加关注
	 * @param uid
	 * @param tid
	 * @return
	 */
	public int addFollow(long uid, long tid);
	/**
	 * 取消关注
	 * @param uid
	 * @param tid
	 * @return
	 */
	public int unFollow(long uid, long tid);
	/**
	 * 获取当前uid关注的用户列表
	 * @return
	 */
	public List<Long> getFollowUids(long uid);
	/**
	 * 获取当前uid的粉丝列表
	 * @param uid
	 * @return
	 */
	public List<Long> getFansUids(long uid);
	/**
	 * 获取用户间关系
	 * @param uid
	 * @param tid
	 * @return 关注状态   (0:无关系 1:已关注 2:好友 3:被关注 -1:黑名单 -2:对方的黑名单 -3:互相黑名单）
	 */
	public int getRelation(long uid,long tid);
	/**
	 * 分页获取关系列表
	 * @param uid
	 * @param relationType
	 * @param page
	 * @param limit (0:全部 !0:指定数量)
	 * @return
	 */
	public List<RelationRecord> getRelationRecord(long uid, int relationType, int page, int limit);
	
	/**
	 * 添加黑名单
	 * @param uid
	 * @param tid
	 * @return
	 */
	public int addBlack(long uid,long tid);
	/**
	 * 取消黑名单
	 * @param uid
	 * @param tid
	 * @return
	 */
	public int deleteBlack(long uid,long tid);
	
	/**
	 * 通知粉丝
	 * @param artId
	 * @param type 1:新增文章 2:新增章节
	 */
	public void noticeFan(long artId, int type);
}
