package com.imlianai.zjdoll.app.modules.publics.msg.leancloud;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgRoom;

/**
 * 主要对外提供消息发送接口
 * @author tensloveq
 *
 */
public interface IMMsgService {
	
	
	/**
	 * 发送个人私聊消息
	 * @return
	 */
	public boolean sendMsg(Msg msg);
	
	/**
	 * 发送房间消息
	 * @return
	 */
	public boolean sendRoomMsg(MsgRoom msgRoom);
	
	/**
	 * 发送系统消息（穿透全部用户收到）
	 * @param msg
	 * @param isOnline 是否在线用户才能收到
	 * @return
	 */
	public boolean sendSysMsg(Msg msg);
	
	/**
	 * 推送仅可以在房间内收到的系统消息
	 * @param msg
	 * @return
	 */
	public boolean sendRoomSysMsg(Msg msg,String conversionId);
	
	/**
	 * 发送推送消息
	 * @return
	 */
	public boolean sendPushNotice(Msg msg);
	
	/**
	 * 发送苹果推送消息
	 * @param appleNotification
	 */
	public void sendPushNotice(AppleNotification appleNotification);
	
}
