package com.imlianai.dollpub.app.modules.support.relation.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.support.relation.router.TableRouter;
import com.imlianai.dollpub.domain.relation.RelationRecord;
import com.imlianai.dollpub.domain.relation.RelationRecord.RelationType;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class RelationDaoImpl implements RelationDao{
	
	@Resource
	private TableRouter tableRouter ;
	@Resource
	private JdbcHandler jdbcHandler;
	
	private String addFollowRecordSql = "insert into %s(uid,followUid,`status`,`time`) values(?,?,?,now()) on duplicate key update `status`=?";
	@Override
	public int addFollowRecord(long uid, long tid, int status) {
		return jdbcHandler.executeSql(String.format(addFollowRecordSql, tableRouter.getTableNameByUid(uid)), uid, tid, status, status);
	}
	
	private String addFollowRecordSecondSql = "insert into %s(uid,followUid,`status`,`time`) values(?,?,?,now()) on duplicate key update `status`=?";
	@Override
	public int addFollowRecordBySecond(long uid, long tid, int status) {
		return jdbcHandler.executeSql(String.format(addFollowRecordSecondSql, tableRouter.getTableNameByUid(tid)), uid, tid, status, status);
	}

	private String getRelationRecord = "select uid ,followUid ,status from %s  where ( (uid=? and followUid=?) or (followUid=? and uid=?) ) ";
	@Override
	public List<RelationRecord> getRelationRecord(long uid, long tid) {
		return jdbcHandler.queryBySql(RelationRecord.class,String.format(getRelationRecord, tableRouter.getTableNameByUid(uid)),  uid, tid, uid, tid);
	}

	private String getFollowRelationRecordSql = "select uid as followUid ,followUid as uid ,status from %s  where uid=? and `status`>=0 order by id desc limit ?,?";
	private String getFollowRelationRecordNoPageSql = "select uid as followUid ,followUid as uid ,status from %s  where uid=? and `status`>=0 ";
	private String getFansRelationRecordSql = "select uid as uid ,followUid as followUid ,status from %s  where followUid=? and `status`>=0 order by id desc limit ?,? ";
	private String getFansRelationRecordNoPageSql = "select uid as uid ,followUid as followUid ,status from %s  where followUid=? and `status`>=0 ";
	private String getBackRelationRecordSql = "select uid as followUid ,followUid as uid ,status from %s  where uid=? and `status`=-2 order by id desc limit ?,? ";
	private String getBackRelationRecordNoPageSql = "select uid as followUid ,followUid as uid ,status from %s  where uid=? and `status`=-2  ";
	@Override
	public List<RelationRecord> getRelationRecordByType(long uid, int type, int startIndex, int pageSize) {
		if (pageSize == 0) {
			if (type == RelationType.Follow.type) {
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getFollowRelationRecordNoPageSql, tableRouter.getTableNameByUid(uid)),  uid);
			} else if (type == RelationType.Black.type) {
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getBackRelationRecordNoPageSql, tableRouter.getTableNameByUid(uid)),uid);
			}else {
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getFansRelationRecordNoPageSql, tableRouter.getTableNameByUid(uid)), uid);
			}
		} else {
			if (type == RelationType.Follow.type) {
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getFollowRelationRecordSql, tableRouter.getTableNameByUid(uid)),  uid, startIndex, pageSize);
			} else if (type == RelationType.Black.type){
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getBackRelationRecordSql, tableRouter.getTableNameByUid(uid)), uid, startIndex, pageSize);
			} else {
				return jdbcHandler.queryBySql(RelationRecord.class,String.format(getFansRelationRecordSql, tableRouter.getTableNameByUid(uid)),  uid, startIndex, pageSize);
			}
		}
	}

}
