package com.imlianai.zjdoll.app.modules.support.event.wechat.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class WechatEventDaoImpl implements WechatEventDao {

	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public int hasGetReward(String unionId) {
		return jdbcHandler.queryCount(
				"select 1 from wechat_reward_records where unionId=? limit 1",
				unionId);
	}

	@Override
	public int addGetReward(long uid, String openId, String unionId, int amt) {
		return jdbcHandler
				.executeSql(
						"insert into wechat_reward_records (uid,unionId,openId,amt,time) value (?,?,?,?,now())",
						uid, unionId, openId, amt);
	}

	@Override
	public int hasGetReward(long uid) {
		return jdbcHandler.queryCount(
				"select 1 from wechat_reward_records where uid=? limit 1", uid);
	}

}
