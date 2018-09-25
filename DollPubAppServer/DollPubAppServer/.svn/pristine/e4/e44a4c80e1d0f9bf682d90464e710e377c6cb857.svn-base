package com.imlianai.dollpub.app.modules.core.doll.pattern.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.pattern.BusPatternStat;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollBusPatternDaoImpl implements DollBusPatternDao {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public BusPatternStat getStat(int busId) {
		return jdbcHandler.queryOneBySql(BusPatternStat.class,
				"select * from doll_bus_pattern_stat where busId=? limit 1",
				busId);
	}

	@Override
	public void initStat(int busId) {
		jdbcHandler
				.executeSql(
						"insert into doll_bus_pattern_stat (busId,time) value (?,now())",
						busId);
	}

	@Override
	public void addStrongRecord(long optId, long logId, long uid, int busId,
			String deviceId, int round, int current, int strong) {
		jdbcHandler.executeSql("insert into doll_bus_strong_record"
				+ "(optId,logId,uid,busId,deviceId,round,current,strong,time) "
				+ "value (?,?,?,?,?,?,?,?,now())", optId, logId, uid, busId,
				deviceId, round, current, strong);
	}

	@Override
	public void updateStat(int busId, int round) {
		if (round == 0) {
			jdbcHandler.executeSql(
					"update doll_bus_pattern_stat set current=current+1,total=total+1,time=now()"
							+ " where busId=?", busId);
		} else {
			jdbcHandler
					.executeSql(
							"update doll_bus_pattern_stat set current=0,total=total+1,round=round+1,time=now()"
									+ " where busId=?", busId);
		}
	}

	@Override
	public void resetStatRound(int busId) {
		jdbcHandler.executeSql(
				"update doll_bus_pattern_stat set round=round+1,current=0,time=now()"
						+ " where busId=?", busId);
	}
}
