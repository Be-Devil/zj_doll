package com.imlianai.zjdoll.app.modules.publics.umeng.service;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.user.UserBase;

/**
 * 主要对外提供消息推送接口
 * 
 * @author tensloveq
 * 
 */
public interface IMPushService {

	/**
	 * 发送推送信息
	 * 
	 * @param msg
	 * @return 
	 */
	public boolean sendPushNotice(Msg msg);

	/**
	 * 发送推送消息
	 * @param toUserBase
	 * @param msg
	 */
	public boolean sendPushNotice(UserBase toUserBase, Msg msg);
	/**
	 * 发送广播消息
	 * @param msgNotice
	 */
	public void sendSysMsg(Msg msg,int osType);

}
