package com.imlianai.dollpub.app.modules.publics.msg.leancloud;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.publics.msg.utils.IMCommonUtil;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgRoom;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 默认情况下发送消息 API 使用异步的方式，调用后直接返回空结果
 * 
 * @author Max
 * 
 */
@Service
public class IMMsgServiceImpl implements IMMsgService {

	private final Logger logger = Logger.getLogger(IMMsgServiceImpl.class);

	@Resource
	private IMCommonService iMCommonService;

	@Override
	public boolean sendMsg(Msg msg) {
		String conv_id = (String) iMCommonService.createSingleConversation(msg
				.getTid());
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("from_peer", "10000");
		List<String> userList = new ArrayList<String>();
		userList.add(msg.getTid() + "");
		postData.put("to_peers", userList);
		Map<String, Object> msgContent = new HashMap<String, Object>();
		Map<String, Object> msgBodyContent = new HashMap<String, Object>();
		Map<String, Object> pushData = new HashMap<String, Object>();
		msgContent.put("_lctype", -1);
		msgBodyContent.put("type", msg.getType());
		msgBodyContent.put("body", msg);
		String msgString = JSON.toJSONString(msgBodyContent);
		msgContent.put("_lctext", msgString);
		String message = JSON.toJSONString(msgContent);
		postData.put("message", message);
		postData.put("transient", false);// 是否不保存历史记录
		postData.put("conv_id", conv_id);
		postData.put("no_sync", false);// 默认情况下消息会被同步给在线的 from_peer 用户的客户端，设置为
										// true 禁用此功能。
		// {"alert":"您有新的消息", "badge":"Increment"}
		if (msg.getPushMsg() != null && !msg.getPushMsg().trim().equals("")) {// 是否显示在推送栏
			pushData.put("alert", msg.getPushMsg());
			pushData.put("badge", "Increment");
			pushData.put("sound", "suanguo_apple.caf");
			postData.put("push_data", JSON.toJSONString(pushData));
		}
		String postDataString = JSON.toJSONString(postData);
		try {
			Map<String, Object> resMap = IMCommonUtil.postDataMaster(
					IMCommonUtil.sendMsgUrlNew, postDataString);
			if (resMap != null) {
				return true;
			}
		} catch (Exception e) {
			logger.info("sendMsg " + msg + " EXCEPTION:" + e.getMessage() + " "
					+ e.getStackTrace());
			logger.error("sendMsg " + msg + " EXCEPTION:" + e.getMessage()
					+ " " + e.getStackTrace()
					+ PrintException.getErrorStack(e, 0));
		}
		return false;
	}



	@Override
	public boolean sendRoomMsg(MsgRoom msgRoom) {
		// String conv_id =
		// iMCommonService.createRoomConversation(msgRoom.getRoomId());
		// String conv_id = "57c52bfca341316fa62f441e";
		String conv_id = msgRoom.getConversationId();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("from_peer", "10000");
		Map<String, Object> msgContent = new HashMap<String, Object>();
		Map<String, Object> msgBodyContent = new HashMap<String, Object>();
		msgBodyContent.put("type", 30);
		msgBodyContent.put("body", msgRoom);
		String msgString = JSON.toJSONString(msgBodyContent);
		msgContent.put("_lctype",-1);
		msgContent.put("_lctext", msgString);
		String message = JSON.toJSONString(msgContent);
		postData.put("message", message);
		postData.put("conv_id", conv_id);
		String postDataString = JSON.toJSONString(postData);
		/* try { */
		IMCommonUtil.postDataMaster(IMCommonUtil.sendMsgUrlNew, postDataString);
		return true;
		/*
		 * } catch (Exception e) { logger.info("sendRoomMsg " + msgRoom +
		 * " EXCEPTION:" + e.getMessage() + " " + e.getStackTrace());
		 * logger.error("sendRoomMsg " + msgRoom + " EXCEPTION:" +
		 * e.getMessage() + " " +
		 * e.getStackTrace()+PrintException.getErrorStack(e, 0)); } return
		 * false;
		 */
	}

	@Override
	public boolean sendPushNotice(Msg msg) {
		long tid = msg.getTid();
		Map<String, Object> postData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> apsMap = new HashMap<String, Object>();
		Map<String, Object> alertMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> pattern = new HashMap<String, Object>();
		pattern.put("$regex", "\\Q" + tid + "\\E");
		whereMap.put("uid", pattern);
		Map<String, Object> msgBody = JSON.parseObject(msg.getBody(),
				new TypeReference<Map<String, Object>>() {
				});
		postData.put("where", whereMap);
		alertMap.put("body", msgBody.get("msg"));
		apsMap.put("alert", alertMap);
		apsMap.put("sound", "suanguo_apple.caf");
		apsMap.put("type", msg.getType());
		apsMap.put("lid", 360257);
		dataMap.put("aps", apsMap);
		postData.put("data", dataMap);
		// postData.put("prod", "online");
		String postDataString = JSON.toJSONString(postData);
		try {
			IMCommonUtil.postDataMaster(IMCommonUtil.sendPushUrlNew,
					postDataString);
			return true;
		} catch (Exception e) {
			logger.info("sendPushNotice " + postData + " EXCEPTION:"
					+ e.getMessage() + " " + e.getStackTrace());
			logger.error("sendPushNotice " + postData + " EXCEPTION:"
					+ e.getMessage() + " " + e.getStackTrace());
		}
		return false;
	}

	@Override
	public void sendPushNotice(AppleNotification appleNotification) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (null == appleNotification.getAppleToken()
				|| appleNotification.getAppleToken().equals("")) {// 使用uid推送
			if (appleNotification.getUid() > 0) {
				Map<String, Object> pattern = new HashMap<String, Object>();
				pattern.put("$regex", "\\Q" + appleNotification.getUid()
						+ "\\E");
				whereMap.put("uid", pattern);
			}
		} else {// 使用token推送
			whereMap.put("deviceToken", appleNotification.getAppleToken());
		}
		if (StringUtil.isNullOrEmpty(whereMap)) {
			logger.info("sendPushNotice 缺乏推送设备信息 appleNotification:"
					+ appleNotification + " ");
			return;
		}
		Map<String, Object> postData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> apsMap = new HashMap<String, Object>();
		Map<String, Object> alertMap = new HashMap<String, Object>();
		postData.put("where", whereMap);
		alertMap.put("body", appleNotification.getAlertMsg());
		apsMap.put("alert", alertMap);
		apsMap.put("sound", "suanguo_apple.caf");
		apsMap.put("type", MsgType.NOTICE_SYS.type);
		if (!StringUtil.isNullOrEmpty(appleNotification.getExtraMap())) {// 有额外的信息
			apsMap.putAll(appleNotification.getExtraMap());
		}
		dataMap.put("aps", apsMap);
		postData.put("data", dataMap);
		String postDataString = JSON.toJSONString(postData);
		try {
			IMCommonUtil.postDataMaster(IMCommonUtil.sendPushUrlNew,
					postDataString);
		} catch (Exception e) {
			logger.info("sendPushNotice " + postData + " EXCEPTION:"
					+ e.getMessage() + " " + e.getStackTrace());
			logger.error("sendPushNotice " + postData + " EXCEPTION:"
					+ e.getMessage() + " " + e.getStackTrace());
		}
	}

	@Override
	public boolean sendSysMsg(Msg msg,boolean isOnline) {
		String conv_id = iMCommonService.createSysConversation();
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("from_peer", "10000");
		Map<String, Object> msgContent = new HashMap<String, Object>();
		Map<String, Object> msgBodyContent = new HashMap<String, Object>();
		Map<String, Object> apsMap = new HashMap<String, Object>();
		msgBodyContent.put("type", msg.getType());
		msg.setTime(DateUtils.getNowTime());
		msgBodyContent.put("body", msg);
		String msgString = JSON.toJSONString(msgBodyContent);
		msgContent.put("_lctype", -1);
		msgContent.put("_lctext", msgString);
		String messageString = JSON.toJSONString(msgContent);
		postData.put("message", messageString);
		postData.put("conv_id", conv_id);
		if (isOnline) {// 在线用户才收到，push为空
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 15);
			postData.put("valid_till", cal.getTimeInMillis());
		}else{// 默认7天后过期
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			postData.put("valid_till", cal.getTimeInMillis());
		}
		try {
			if (!StringUtil.isNullOrEmpty(msg.getPushMsg())) {
				apsMap.put("alert", msg.getPushMsg());
				apsMap.put("sound", "suanguo_apple.caf");
				apsMap.put("badge", "Increment");
				if (AppUtils.isTestEnv()) {
					apsMap.put("_profile", "test");
				}
				if (!isOnline) {//在线消息不产生push
					postData.put("push", JSON.toJSONString(apsMap));
				}
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		String postDataString = JSON.toJSONString(postData);
		try {
			logger.info("sendSysMsg conv_id:" + conv_id + " ");
			IMCommonUtil.postDataMaster(IMCommonUtil.sendBrocastUrlNew,
					postDataString);
			return true;
		} catch (Exception e) {
			logger.info("sendSysMsg " + msg + " EXCEPTION:" + e.getMessage()
					+ " " + e.getStackTrace());
			logger.error("sendSysMsg " + msg + " EXCEPTION:" + e.getMessage()
					+ " " + e.getStackTrace()
					+ PrintException.getErrorStack(e, 0));
		}
		return false;
	}
}
