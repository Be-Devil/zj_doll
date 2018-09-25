package com.imlianai.zjdoll.app.modules.core.doll.robot.service;

import com.imlianai.zjdoll.domain.doll.DollBus;

public interface DollRobotService {

	/**
	 * 增加机器人
	 * @param bus
	 * @param num
	 */
	public void addRobot(final DollBus bus,final int num);

	
	/**
	 * 移除机器人
	 * @param bus
	 * @param num
	 */
	public void removeRobot(int busId);
	
	/**
	 * 初始化机器人
	 */
	public void initRobotUser();


	/**
	 * 处理机器人捉取结果消息
	 * @param bus
	 * @param success
	 */
	public void handleRobotCatchResultMsg(DollBus bus, boolean success);
	
	/**
	 * 获取机器人UID
	 */
	public Long getRandomRobot();
}
