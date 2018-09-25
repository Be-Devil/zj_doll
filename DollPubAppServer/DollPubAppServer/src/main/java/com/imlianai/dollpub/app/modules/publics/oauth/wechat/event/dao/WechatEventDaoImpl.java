package com.imlianai.dollpub.app.modules.publics.oauth.wechat.event.dao;

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

	@Override
	public int hasGetSignReward(long uid) {
		return jdbcHandler
				.queryCount(
						"select 1 from wechat_sign_reward_record where uid=? and dateCol=DATE_FORMAT(now(),'%Y-%m-%d') limit 1",
						uid);
	}

	@Override
	public int addGetSignReward(long uid, int reward) {
		return jdbcHandler
				.executeSql(
						"insert into wechat_sign_reward_record (uid,dateCol,reward,time) value (?,DATE_FORMAT(now(),'%Y-%m-%d'),?,now())",
						uid, reward);
	}

}
