package com.imlianai.dollpub.app.modules.support.robot.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.robot.domain.DollShopRobot;

public interface DollRobotService {

	/**
	 * 初始化机器人
	 */
	public void initRobotUser();

	/**
	 * 获取机器人UID
	 */
	public Long getRandomRobotUid();

	/**
	 * 初始化店主机器人
	 * 
	 * @param uids
	 * @return
	 */
	public int initShopkeeperUid();

	/**
	 * 获取店主机器人
	 * 
	 * @return
	 */
	List<DollShopRobot> getShopkeeperUid();

	/**
	 * 定时处理机器人充值
	 */
	void handleShopRobot();
}
