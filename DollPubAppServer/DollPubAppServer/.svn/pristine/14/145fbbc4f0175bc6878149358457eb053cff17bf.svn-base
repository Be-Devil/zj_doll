package com.imlianai.dollpub.app.modules.support.robot.dao;

import com.imlianai.dollpub.app.modules.support.robot.domain.DollShopRobot;
import com.imlianai.dollpub.domain.user.UserGeneral;

import java.util.List;

public interface DollRobotDao {

	/**
	 * 获取初始机器人信息
	 * @return
	 */
	public List<UserGeneral> getInitUserData();

	/**
	 * 记录机器人
	 * @param uid
	 * @param gender
	 */
	public void addRobotPool(long uid, int gender);
	
	/**
	 * 加载全部机器人
	 * @return
	 */
	public List<Long> getRobotPool();


	Long getRobotPoolUid(long uid);
	
	/**
	 * 增加店主机器人id
	 * @param uids
	 * @return
	 */
	public int addShopkeeperUid(DollShopRobot robot);

	/**
	 * 获取店主机器人
	 * @return
	 */
	List<DollShopRobot> getShopkeeperUid();
	
	/**
	 * 增加每日充值额
	 * @param uid
	 * @param inc
	 * @return
	 */
	public int addDailyAmt(long uid,int inc);

}
