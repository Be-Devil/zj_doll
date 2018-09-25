package com.imlianai.zjdoll.app.modules.publics.weixinAccount.service;


public interface WebWeixinKefuService {
	/**
	 * 发送客服消息
	 * @param receviedUser 收到方OpenId
	 * @param senderUser 发送方即公众平台
	 * @param text 消息内容
	 * @param type 消息种类-暂缺
	 * @return
	 */
	public String sendKefuMessage(String receviedUser,String senderUser, String text,int type);

}
