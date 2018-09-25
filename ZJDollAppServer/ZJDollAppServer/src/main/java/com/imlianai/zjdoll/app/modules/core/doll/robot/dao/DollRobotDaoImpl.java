package com.imlianai.zjdoll.app.modules.core.doll.robot.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.core.doll.robot.domain.BusRobotStat;

@Repository
public class DollRobotDaoImpl implements DollRobotDao {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public List<Long> getRobotUids() {
		return null;
	}

	@Override
	public List<UserGeneral> getInitUserData() {
		return jdbcHandler.queryBySql(UserGeneral.class,
				"select * from robot_user_general");
	}

	@Override
	public void addRobotBusRecord(long uid, int busId) {
		jdbcHandler
				.executeSql(
						"insert into doll_bus_robot_watch_record (buisId,robotUid,time) value (?,?,now()) on duplicate key update buisId=?,time=now()",
						busId, uid, busId);
	}

	@Override
	public int isInBus(long uid, int busId) {
		return jdbcHandler
				.queryCount(
						"select 1 from doll_bus_robot_watch_record where buisId= ? and robotUid=? limit 1",
						busId, uid);
	}

	@Override
	public int incFakeRobotNum(int busId, int num) {
		return jdbcHandler.executeSql(
				"insert into bus_robot_stat (busId,fakeNum) value (?,?)"
						+ " on duplicate key update fakeNum=?+fakeNum", busId,
				num, num);
	}

	@Override
	public void decFakeRobotNum(int busId, int num) {
		jdbcHandler.executeSql(
				"update bus_robot_stat set fakeNum=fakeNum-? where busId=? ",
				num, busId);
	}

	@Override
	public BusRobotStat getBusRobotStat(int busId) {
		return jdbcHandler.queryOneBySql(BusRobotStat.class,
				"select * from bus_robot_stat where busId=? ", busId);
	}

	@Override
	public void addRobotPool(long uid, int gender) {
		jdbcHandler
				.executeSql(
						"insert into robot_user_pool (uid,gender,time) value (?,?,now())",
						uid, gender);
	}

	@Override
	public List<Long> getRobotPool() {
		return jdbcHandler.queryBySql(Long.class,
				"select uid from robot_user_pool");
	}

	@Override
	@CacheWrite(validTime = 10)
	public int getRobotCount(int busId) {
		return jdbcHandler
				.queryCount(
						"select count(0) from doll_bus_robot_watch_record where buisId=?",
						busId);
	}

	@Override
	public void removeRobotBusRecord(int busId, int num) {
		jdbcHandler
				.executeSql(
						"delete from doll_bus_robot_watch_record where buisId= ?  limit ? ",
						busId, num);
	}

	@Override
	public List<Long> getRobotBusRecord(int busId,int num) {
		return jdbcHandler.queryBySql(Long.class,"select robotUid from doll_bus_robot_watch_record where buisId= ?  limit ? ",busId,num);
	}

}
