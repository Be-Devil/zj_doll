package com.imlianai.dollpub.app.modules.core.doll.service;

import com.imlianai.dollpub.app.modules.core.doll.vo.DollApplyRes;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.optrecord.OptRecord;

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
	public void abandonMachine(long uid,int busId);
	
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
	public void leaveBus(long uid,int busId);
	
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
	
	void sendSuccessMsg(DollBus bus, DollOptRecord record, int group);

	void sendFailMsg(DollBus bus, DollOptRecord record);

	/**
	 * 发送上机消息
	 * @param uid
	 * @param dollBus
	 */
	public void sendApplyMsg(long uid, DollBus dollBus);

	/**
	 * 发送失败消息
	 * @param bus
	 * @param record
	 */
	void sendFailMsg(DollBus bus, OptRecord record);

	/**
	 * 发送成功消息
	 * @param bus
	 * @param record
	 */
	void sendSuccessMsg(DollBus bus, OptRecord record);

	/**
	 * 捉取结果
	 * @param bus
	 * @param optRecord
	 * @param isSuccess
	 * @param group
	 */
	void handleH5ResultMsg(DollBus bus, OptRecord optRecord, boolean isSuccess,
			int group);

	/**
	 * 单独发送websocket房间
	 * @param bus
	 * @param optRecord
	 * @param isSuccess
	 * @param group
	 */
	void handleH5ResultMsgOnly(DollBus bus, OptRecord optRecord, boolean isSuccess, int group);
}
