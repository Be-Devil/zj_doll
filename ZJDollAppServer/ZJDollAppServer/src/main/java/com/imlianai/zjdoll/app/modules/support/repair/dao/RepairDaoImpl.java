package com.imlianai.zjdoll.app.modules.support.repair.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class RepairDaoImpl implements RepairDao {

	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public int addReocrd(int busId, long uid, String reason) {
		return jdbcHandler.executeSql("INSERT INTO `repair_record`(`uid`,`busId`,`reason`,`time`)VALUES(?,?,?,now());", uid,busId,reason);
	}

}
