package com.imlianai.zjdoll.app.modules.core.doll.robot.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.app.modules.core.doll.robot.domain.BusRobotStat;

public interface DollRobotDao {

	/**
	 * 获取全部机器人
	 * 
	 * @return
	 */
	public List<Long> getRobotUids();

	/**
	 * 记录机器人在机情况
	 * 
	 * @param uid
	 * @param busId
	 */
	public void addRobotBusRecord(long uid, int busId);
	
	/**
	 * 移除机器人在机记录
	 * @param busId
	 * @param num
	 */
	public void removeRobotBusRecord(int busId,int num);
	
	/**
	 * 获取机内机器人
	 * @param num
	 * @return
	 */
	public List<Long> getRobotBusRecord(int busId,int num);

	/**
	 * 判断机器人是否在机
	 * 
	 * @param uid
	 * @param busId
	 * @return
	 */
	public int isInBus(long uid, int busId);

	/**
	 * 增加伪作机器人数
	 * 
	 * @param busId
	 * @param num
	 * @return
	 */
	public int incFakeRobotNum(int busId, int num);

	/**
	 * 减少机器人数
	 * 
	 * @param busId
	 * @param num
	 */
	public void decFakeRobotNum(int busId, int num);

	/**
	 * 机器人状态
	 * @param busId
	 * @return
	 */
	public BusRobotStat getBusRobotStat(int busId);

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
	public void addRobotPool(long uid,int gender);
	
	/**
	 * 加载全部机器人
	 * @return
	 */
	public List<Long> getRobotPool();
	
	/**
	 * 获取机器人数
	 * @param busId
	 * @return
	 */
	public int getRobotCount(int busId);

}
