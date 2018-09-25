package com.imlianai.dollpub.app.modules.support.robot.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.support.robot.domain.DollShopRobot;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollRobotDaoImpl implements DollRobotDao {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public List<UserGeneral> getInitUserData() {
		return jdbcHandler.queryBySql(UserGeneral.class, "select * from robot_user_general");
	}

	@Override
	public void addRobotPool(long uid, int gender) {
		jdbcHandler.executeSql("insert into robot_user_pool (uid,gender,status,time) value (?,?,?,now())", uid, gender,0);
	}

	@Override
	public List<Long> getRobotPool() {
		return jdbcHandler.queryBySql(Long.class, "select uid from robot_user_pool");
	}

	@Override
	public Long getRobotPoolUid(long uid) {
		return jdbcHandler.queryOneBySql(Long.class,"select uid from robot_user_pool where uid=?",uid);
	}

	@Override
	public int addShopkeeperUid(DollShopRobot robot) {
		return jdbcHandler.executeSql("insert into robot_shopkeeper_uid (uid,datecol,dailyLimit,amt) value (?,DATE_FORMAT(now(),'%Y-%m-%d'),?,0)", robot.getUid(),robot.getDailyLimit());
	}
	@Override
	public List<DollShopRobot> getShopkeeperUid(){
		return jdbcHandler.queryBySql(DollShopRobot.class, "select * from robot_shopkeeper_uid where datecol=DATE_FORMAT(now(),'%Y-%m-%d')");
	}

	@Override
	public int addDailyAmt(long uid, int inc) {
		return jdbcHandler.executeSql("update robot_shopkeeper_uid set amt=amt+? where uid=? and datecol=DATE_FORMAT(now(),'%Y-%m-%d')", inc,uid);
	}
}
