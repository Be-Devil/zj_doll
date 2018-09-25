package com.imlianai.dollpub.app.modules.publics.msg.leancloud;

import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgRoom;

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
	public boolean sendSysMsg(Msg msg,boolean isOnline);
	
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
