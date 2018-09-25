package com.imlianai.zjdoll.app.modules.support.exchange.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollRecycleRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollComposeDaoImpl implements DollComposeDao {

	@Resource
	JdbcHandler jdbcHandler;
	
	String saveRecycleRecord = "insert into doll_recycle_record(uDollId,jewel,createTime) values(?,?,now())";
	@Override
	public int saveRecycleRecord(long uDollId, int jewel) {
		return jdbcHandler.executeSql(saveRecycleRecord, uDollId, jewel);
	}
	
	String getRecycleRecordByUDollId = "select * from doll_recycle_record where uDollId = ?";
	@Override
	public DollRecycleRecord getRecycleRecordByUDollId(Long uDollId) {
		return jdbcHandler.queryOneBySql(DollRecycleRecord.class, getRecycleRecordByUDollId, uDollId);
	}

}
