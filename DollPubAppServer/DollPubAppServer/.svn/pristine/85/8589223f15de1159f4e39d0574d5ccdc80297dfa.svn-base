package com.imlianai.dollpub.app.modules.publics.msg.service;

import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgRoom;

/**
 * 消息相关
 * 
 * @author Max
 * 
 */
public interface MsgService {

	/**
	 * 发送普通消息
	 * 
	 * @param msg
	 */
	public void sendMsg(Msg msg);
	
	/**
	 * 发送全站消息
	 * @param msg
	 */
	public void sendSysMsg(Msg msg);
	
	/**
	 * 发送全站在线消息
	 * @param msg
	 */
	public void sendOnlineSysMsg(Msg msg);

	/**
	 * 推送友盟普通消息
	 * 
	 * @param msg
	 */
	public void pushUMengMsg(Msg msg);

	/**
	 * 发送房间消息
	 * 
	 * @param msgRoom
	 */
	public void sendMsgRoom(MsgRoom msgRoom);

	/**
	 * 发送所有房间消息
	 * 
	 * @param msgRoom
	 */
	public void sendMsgRoomAll(MsgRoom msgRoom);

	/**
	 * 推送全站图文消息
	 * 
	 * @param msg
	 */
	public void pushMediaMsgAll(Msg msg);

	/**
	 * /** 更新苹果推送token
	 * 
	 * @param uid
	 * @param token
	 * @param channel
	 * @return
	 */
	public int updateToken(long uid, String token, String channel);

}
