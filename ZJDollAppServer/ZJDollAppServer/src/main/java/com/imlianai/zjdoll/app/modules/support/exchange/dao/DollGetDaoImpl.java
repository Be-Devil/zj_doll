package com.imlianai.zjdoll.app.modules.support.exchange.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollGetRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollGetDaoImpl implements DollGetDao {
	
	@Resource
	JdbcHandler jdbcHandler;

	private String saveDollGetRecord = "insert into doll_get_record(uDollId,uid,phone,type,status,remark,createTime) values(?,?,?,?,?,?,now())";
	@Override
	public int saveDollGetRecord(DollGetRecord record) {
		return jdbcHandler.executeSql(saveDollGetRecord, record.getuDollId(), record.getUid(), record.getPhone()
				, record.getType(), record.getStatus(), record.getRemark());
	}
	
	private String getDollGetRecord = "select * from doll_get_record where uDollId=?";
	@Override
	public DollGetRecord getDollGetRecord(Long uDollId) {
		return jdbcHandler.queryOneBySql(DollGetRecord.class, getDollGetRecord, uDollId);
	}

}