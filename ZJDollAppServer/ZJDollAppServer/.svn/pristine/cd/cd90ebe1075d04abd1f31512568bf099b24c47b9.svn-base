package com.imlianai.zjdoll.app.modules.publics.msg.leancloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.CommonUtil;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.utils.IMCommonUtil;

@Service
public class IMCommonServiceImpl implements IMCommonService {

	private final BaseLogger logger = BaseLogger
			.getLogger(IMCommonServiceImpl.class);

	@Resource
	private UserService userService;

	/**
	 * 创建系统对话
	 * 
	 * @return
	 */
	@Override
	public String createSysConversation() {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", "sys_msg" + System.currentTimeMillis());
		postData.put("sys", true);
		String postDataString = JSON.toJSONString(postData);
		Map<String, Object> resMap = IMCommonUtil.postData(
				IMCommonUtil.createRoomUrlNew, postDataString);
		String conv_id = (String) resMap.get("objectId");
		logger.info("createSysConversation 系统对话ID" + conv_id);
		return conv_id;
	}

	/**
	 * 创建直播间对话
	 * 
	 * @return
	 */
	@Override
	public String createRoomConversation(long liveId) {
		String conv_id = null;
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", "live_room_" + liveId);
		postData.put("tr", true);
		String postDataString = JSON.toJSONString(postData);
		Map<String, Object> resInfoMap = null;
		try {
			Map<String, Object> resMap = IMCommonUtil.postData(
					IMCommonUtil.createRoomUrlNew, postDataString);
			resInfoMap = resMap;
			conv_id = (String) resMap.get("objectId");
			logger.info("createRoomConversation 直播ID:" + liveId + " 直播间对话ID:"
					+ conv_id);
		} catch (Exception e) {
			logger.error("createRoomConversation 创建直播间聊天室失败 EXCEPTION 直播ID:"
					+ liveId + " e:" + e.getMessage() + "resInfoMap:"
					+ resInfoMap);
		}
		return conv_id;
	}

	@Override
	public String createSingleConversation(long uid) {
		// 先找出是否已有创建了的单聊对话,不存在则创建
		String conv_id = "";// iMDAO.getUserConversation(uid);
		if (conv_id != null && !conv_id.equals("")) {
			return conv_id;
		}
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", "10000_" + uid);
		List<String> userList = new ArrayList<String>();
		userList.add("10000");
		userList.add(uid + "");
		// postData.put("sys", true);
		postData.put("m", userList);
		String postDataString = JSON.toJSONString(postData);
		Map<String, Object> resMap = IMCommonUtil.postData(
				IMCommonUtil.createRoomUrlNew, postDataString);
		conv_id = (String) resMap.get("objectId");
		logger.info("createSingleConversation uid:" + uid + " 单聊对话ID:"
				+ conv_id);
		if (conv_id != null && !conv_id.equals("")) {
			// iMDAO.addUserConversation(uid, conv_id);
		}
		return conv_id;
	}

	@Override
	public Map<String, Object> getLoginSign(UserBase userBase) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		// appid:clientid::timestamp:nonce
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(userBase.getUid() + "::"
				+ timestamp + ":" + nonce);
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public Map<String, Object> getLoginSign(String uid) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		// appid:clientid::timestamp:nonce
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(uid + "::" + timestamp + ":"
				+ nonce);
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public Map<String, Object> getEnterRoomSign(DollBus liveInfo, long uid) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		// appid:clientid:convid:sorted_member_ids:timestamp:nonce:action
		// action-此次行为的动作，分为add（加群和邀请）与remove（踢出群）两种，但出于兼容考虑，签名时分别使用常量invite和kick来进行表示。
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(uid + ":"
				+ liveInfo.getConversationId() + ":" + uid + ":" + timestamp
				+ ":" + nonce + ":invite");
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public Map<String, Object> getEnterRoomSign(String convId, long uid,
			String action) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		// appid:clientid:convid:sorted_member_ids:timestamp:nonce:action
		// action-此次行为的动作，分为add（加群和邀请）与remove（踢出群）两种，但出于兼容考虑，签名时分别使用常量invite和kick来进行表示。
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(uid + ":" + convId + ":" + uid
				+ ":" + timestamp + ":" + nonce + ":" + action);
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public Map<String, Object> getEnterRoomSign(String convId, String uid,
			String action) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		// appid:clientid:convid:sorted_member_ids:timestamp:nonce:action
		// action-此次行为的动作，分为add（加群和邀请）与remove（踢出群）两种，但出于兼容考虑，签名时分别使用常量invite和kick来进行表示。
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(uid + ":" + convId + ":" + uid
				+ ":" + timestamp + ":" + nonce + ":" + action);
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public Map<String, Object> getCreateRoomSign(long uid, long tid) {
		Map<String, Object> signMap = new HashMap<String, Object>();
		String sorted_member_ids = "";
		if (uid < tid) {
			sorted_member_ids = uid + ":" + tid;
		} else {
			sorted_member_ids = tid + ":" + uid;
		}
		// appid:clientid:sorted_member_ids:timestamp:nonce
		long timestamp = System.currentTimeMillis() / 1000;
		String nonce = AppUtils.randomStr(CommonUtil.randomNonceStr, 5);
		String sign = IMCommonUtil.getSign2(uid + ":" + sorted_member_ids + ":"
				+ timestamp + ":" + nonce);
		signMap.put("timestamp", timestamp);
		signMap.put("nonce", nonce);
		signMap.put("sign", sign);
		return signMap;
	}

	@Override
	public boolean doKickOffByUid(long uid) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("client_id", uid + "");
		postData.put("reason", "用户违规");
		String postDataString = JSON.toJSONString(postData);
		IMCommonUtil.postDataMaster(IMCommonUtil.kickOffUrl, postDataString);
		return true;
	}

	public boolean isUidOnline(long uid) {
		Map<String, Object> postData = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add(uid + "");
		list.add(1542957 + "");
		postData.put("peers", list);
		String postDataString = JSON.toJSONString(postData);
		IMCommonUtil.postDataMaster(IMCommonUtil.isOnlineUrl, postDataString);
		return true;

	}

	public static void main(String[] args) {
		new IMCommonServiceImpl().removeBlockUid(2994898l,
				"58babc4a333a1307caf3e8fc");

	}

	@Override
	public boolean doBlockUid(long uid) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("client_id", uid + "");
		postData.put("ttl", 3600 * 5);
		String postDataString = JSON.toJSONString(postData);
		IMCommonUtil.postDataMaster(IMCommonUtil.blockUserUrl, postDataString);
		return true;
	}

	@Override
	public boolean doBlockUid(long uid, String conversationId) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("client_id", uid + "");
		postData.put("conv_id", conversationId);
		postData.put("ttl", 3600 * 5);
		String postDataString = JSON.toJSONString(postData);
		IMCommonUtil.postDataMaster(IMCommonUtil.blockUserUrl, postDataString);
		return true;
	}

	@Override
	public boolean removeBlockUid(long uid, String conversationId) {
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("client_id", uid + "");
		postData.put("conv_id", conversationId);
		return IMCommonUtil.deleteDataMaster(IMCommonUtil.blockUserUrl,
				postData);
	}

}
