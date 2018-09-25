package com.imlianai.zjdoll.app.modules.support.record.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.record.FirsttimeMsgPushRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class FirsttimeMsgPushRecordDaoImpl implements FirsttimeMsgPushRecordDao {

	@Resource
	JdbcHandler jdbcHandler;
	
	private String getFirsttimeMsgPushRecord = "select * from firsttime_msg_push_record where uid=? and type=? and num=?";
	@Override
	public FirsttimeMsgPushRecord getFirsttimeMsgPushRecord(Long uid, int type, int num) {
		return jdbcHandler.queryOneBySql(FirsttimeMsgPushRecord.class, getFirsttimeMsgPushRecord, uid, type, num);
	}
	
	private String saveFirsttimeMsgPushRecord = "insert into firsttime_msg_push_record(uid,type,num,createTime) values(?,?,?,now())";
	@Override
	public int saveFirsttimeMsgPushRecord(Long uid, int type, int num) {
		return jdbcHandler.executeSql(saveFirsttimeMsgPushRecord, uid, type, num);
	}
}
