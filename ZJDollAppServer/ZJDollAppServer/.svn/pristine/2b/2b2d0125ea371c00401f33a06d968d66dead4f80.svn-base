package com.imlianai.zjdoll.app.modules.core.doll.service;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollApplyRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;

/**
 * 夹娃娃记录
 * @author tensloveq
 *
 */
public interface DollService {

	/**
	 * 申请上机
	 * @param uid
	 * @param busId
	 * @return 操作id
	 */
	public DollApplyRes applyMachine(long uid,DollBus dollBus);
	
	/**
	 * 放弃上机
	 * @param uid
	 * @param busId
	 */
	public BusRedpacket abandonMachine(long uid,int busId);
	
	/**
	 * 进入直播间
	 * @param uid
	 * @param busId
	 * @return
	 */
	public int enterBus(long uid,int busId);
	
	/**
	 * 离开直播间
	 * @param uid
	 * @param busId 当busId=-2时，会清除当前所在直播间记录及数量
	 */
	public void leaveBus(long uid,int busId,boolean isRobot);
	
	/**
	 * 操作娃娃机
	 * @param uid
	 * @param busId
	 * @param action
	 * @return
	 */
	public int handleOpt(long uid,int busId,int action);
	
	/**
	 * 释放
	 * @param busId
	 * @return
	 */
	public int releaseBus(int busId);
	
	/**
	 * 初始化会话
	 * @param dollBus
	 * @return
	 */
	public DollBus initBusConversationId(DollBus dollBus);
	
	/**
	 * 处理夹取结果
	 * @param dollBus
	 * @param isSuccess
	 * @param deviceCompany
	 */
	public void handleResult(long logId,int isSuccess, int deviceCompany);
	
}
