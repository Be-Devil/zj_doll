package com.imlianai.zjdoll.app.modules.support.ranking.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollRankReward;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class RankingDaoImpl implements RankingDao {

	@Resource
	JdbcHandler jdbcHandler;

	String addChargeSum = "insert into charge_rank_week_summry(uid,weekCode,amt,time) values(?,?,?,now()) "
			+ " on duplicate key update amt=amt+?,time=now()";

	@Override
	public int addChargeSum(int cost, long uid, String weekCode) {
		return jdbcHandler.executeSql(addChargeSum, uid, weekCode, cost, cost);
	}

	String addRichValue = "insert into charge_rank_week_rich_summry(uid,weekCode,value,time) values(?,?,?,now()) "
			+ " on duplicate key update value=?,time=now()";

	@Override
	public int addRichValue(double cost, long uid, String weekCode) {
		return jdbcHandler.executeSql(addRichValue, uid, weekCode, cost, cost);
	}

	@Override
	public List<RankingItem> getRichRank(String weekCode, int size) {
		return jdbcHandler
				.queryBySql(
						RankingItem.class,
						"select * from charge_rank_week_rich_summry where weekCode=? order by value desc limit ?",
						weekCode, size);
	}

	@Override
	public int getChargeSum(long uid, String weekCode) {
		return jdbcHandler.queryCount("select amt from charge_rank_week_summry where  uid=? and weekCode=?",uid, weekCode);
	}

	@Override
	public RankingItem getRichValue(long uid, String weekCode) {
		return jdbcHandler.queryOneBySql(RankingItem.class, "select * from charge_rank_week_rich_summry where  uid=? and weekCode=?",uid, weekCode);
	}

	@Override
	public List<DollRankReward> getRewardListByType(int type) {
		return jdbcHandler.queryBySql(DollRankReward.class,"select * from doll_rank_reward where type=? order by `rank` asc",type);
	}

}
