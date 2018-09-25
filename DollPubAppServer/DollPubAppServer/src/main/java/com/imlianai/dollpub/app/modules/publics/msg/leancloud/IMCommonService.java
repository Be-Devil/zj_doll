package com.imlianai.dollpub.app.modules.publics.msg.leancloud;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.user.UserBase;

/**
 * leanCloud通用接口
 * 
 * @author tensloveq
 * 
 */
public interface IMCommonService {

	/**
	 * 创建系统会话
	 * 
	 * @return 会话ID
	 */
	public String createSysConversation();

	/**
	 * 创建用户单聊会话
	 * 
	 * @param liveId
	 * @return 会话ID
	 */
	public String createSingleConversation(long uid);

	/**
	 * 创建聊天室会话
	 * 
	 * @param liveId
	 * @return 会话ID
	 */
	public String createRoomConversation(long liveId);

	/**
	 * 获取用户登录所需的签名
	 * 
	 * @param userBase
	 * @return
	 */
	public Map<String, Object> getLoginSign(UserBase userBase);

	/**
	 * 获取登录签名--主要用于网页端
	 * @param uid
	 * @return
	 */
	public Map<String, Object> getLoginSign(String uid);
	
	/**
	 * 获取创建聊天室所需签名
	 * @param uid 发起方
	 * @param tid 接收方
	 * @return
	 */
	public Map<String, Object> getCreateRoomSign(long uid,long tid);

	/**
	 * 获取用户进入聊天室所需的签名
	 * 
	 * @param liveInfo
	 * @param uid
	 * @return
	 */
	public Map<String, Object> getEnterRoomSign(DollBus liveInfo, long uid);
	
	/**
	 * 获取用户进入聊天室所需的签名
	 * @param convId
	 * @param uid
	 * @param action
	 * @return
	 */
	public Map<String, Object> getEnterRoomSign(String convId, long uid,String action);
	
	/**
	 * 获取用户进入聊天室所需的签名--用于web端
	 * @param convId
	 * @param uid
	 * @param action
	 * @return
	 */
	public Map<String, Object> getEnterRoomSign(String convId, String uid,String action);
	
	/**
	 * 踢用户下线
	 * @param uid
	 * @return
	 */
	public boolean doKickOffByUid(long uid);
	
	/**
	 * 禁言
	 * @param uid
	 * @return
	 */
	public boolean doBlockUid(long uid);
	
	/**
	 * 在某个会话中禁言某用户
	 * @param uid
	 * @param conversationId
	 * @return
	 */
	public boolean doBlockUid(long uid,String conversationId);
	
	/**
	 * 解除某会话中某用户的禁言
	 * @param uid
	 * @param conversationId
	 * @return
	 */
	public boolean removeBlockUid(long uid,String conversationId);
	
}
