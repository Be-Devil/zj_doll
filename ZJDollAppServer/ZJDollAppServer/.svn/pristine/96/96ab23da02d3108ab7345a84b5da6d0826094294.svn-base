package com.imlianai.zjdoll.app.modules.publics.msg.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class MsgDaoImpl implements MsgDao {
	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public void addPushNum(long uid, long pushId) {
		jdbcHandler
				.executeSql(
						"INSERT INTO `doll_app`.`msg_push_num`(`uid`,`pushId`,`time`,`dateCol`)VALUES(?,?,now(),DATE_FORMAT(now(),'%Y-%m-%d'));",
						uid, pushId);
	}

}
