package com.imlianai.zjdoll.app.modules.publics.umeng.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.AndroidBroadcast;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.AndroidUnicast;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.IOSBroadcast;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.IOSNotification;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.IOSUnicast;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.PushClient;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.UMengConstants;
import com.imlianai.zjdoll.app.modules.publics.umeng.common.AndroidNotification.DisplayType;
import com.imlianai.zjdoll.app.modules.publics.umeng.service.IMPushService;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.service.EventNewyearRecharge20180207ServiceImpl;

@Service
public class IMPushServiceImpl implements IMPushService {

	@Resource
	private UserService userService;

	
	 private static final BaseLogger logger = BaseLogger.getLogger(IMPushServiceImpl.class);

	@Override
	public boolean sendPushNotice(Msg msg) {
		try {
			UserBase toUserBase = userService.getUserBase(msg.getTid());
			boolean sendStatus=sendPushNotice(toUserBase, msg);
			return sendStatus;
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		return false;
	}

	/**
	 * 发送安卓推送
	 * 
	 * @param deviceToken
	 * @param msgText
	 * @return 
	 * @throws Exception
	 */
	private boolean sendAndroidUnicast(String deviceToken, String msgText,String APPKey,String Master_Secret) {
		AndroidUnicast unicast;
		try {
			unicast = new AndroidUnicast(APPKey, Master_Secret);
			unicast.setDisplayType(DisplayType.MESSAGE);
			unicast.setDeviceToken(deviceToken);
			unicast.setCustomField(msgText);
			if (!AppUtils.isTestEnv()) {
				unicast.setProductionMode();
			} else {
				unicast.setTestMode();
			}
			PushClient client = new PushClient();
			client.send(unicast);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendPushNotice(UserBase toUserBase, Msg msg) {
		if (toUserBase==null||StringUtil.isNullOrEmpty(toUserBase.getUmengToken())) {
			return false;
		}
		String channel = toUserBase.getChannel();
		if (toUserBase.getOsType() == 0) {
			Map<String, Object> msgBody = new HashMap<String, Object>();
			msgBody.put("_lctype", msg.getType());
			Map<String, Object> msgBodyContent = new HashMap<String, Object>();
			msgBodyContent.put("type", msg.getType());
			Map<String, Object> msgMap =null;
			msgBodyContent.put("body", msg);
			String msgBodyString = JSON.toJSONString(msgBodyContent);
			msgBody.put("_lctext", msgBodyString);
			String msgString = JSON.toJSONString(msgBody);
			String channelString=UMengConstants.AndroidDefault;
			String info=UMengConstants.AndroidChannel_AppKey_Secret_Map.get(channelString);
			try {
				channelString=channel.split("_")[0];
			} catch (Exception e) {
			}
			if (UMengConstants.AndroidChannel_AppKey_Secret_Map.get(channelString)!=null) {
				info=UMengConstants.AndroidChannel_AppKey_Secret_Map.get(channelString);
			}
			boolean sendStatus=sendAndroidUnicast(toUserBase.getUmengToken(), msgString,info.split(",")[0],info.split(",")[2]);
			return sendStatus;
		} else {
			Map<String, Object> customizedMap = null;
			if (UMengConstants.IosChannel_AppKey_Secret_Map.get(channel)!=null) {
				String appKey = UMengConstants.IosChannel_AppKey_Secret_Map.get(channel).split(",")[0];
				String appSecret = UMengConstants.IosChannel_AppKey_Secret_Map.get(channel).split(",")[1];
				boolean sendStatus=sendIOSUnicast(toUserBase.getUmengToken(), msg, customizedMap, appKey, appSecret);
				return sendStatus;
			}else{
				return true;
			}
		}
	}
	public static void main(String[] args) {
		String bodyString = "#春暖花开，充值送礼# 小主福气值为" + 0
				+ "，暂列贵人榜第100名。";
		MsgNotice msg = new MsgNotice(1895586, MsgType.NOTICE_SYS.type,
				bodyString);
		msg.setPushMsg(bodyString);
		msg.setJumpWeb(EventNewyearRecharge20180207ServiceImpl.EVENT_URL + "?uid=" + 1895586);
		msg.setTargetTitle("春暖花开，充值送礼");
		msg.setTargetName("春暖花开，充值送礼");
				Map<String, Object> msgBody = new HashMap<String, Object>();
				msgBody.put("_lctype", msg.getType());
				Map<String, Object> msgBodyContent = new HashMap<String, Object>();
				msgBodyContent.put("type", msg.getType());
				Map<String, Object> msgMap =null;
				msgBodyContent.put("body", msg);
				String msgBodyString = JSON.toJSONString(msgBodyContent);
				msgBody.put("_lctext", msgBodyString);
				String msgString = JSON.toJSONString(msgBody);
	}

	/**
	 * 发送苹果推送
	 * 
	 * @param deviceToken
	 * @param msgText
	 * @param customizedMap
	 * @return 
	 */
	private boolean sendIOSUnicast(String deviceToken, Msg msg, Map<String, Object> customizedMap, String appKey,
			String appSecret) {
		IOSUnicast unicast = null;
		try {
			unicast = new IOSUnicast(appKey, appSecret);
			unicast.setDeviceToken(deviceToken);
			fillIOSNotification(msg, unicast, customizedMap);
			PushClient client = new PushClient();
			client.send(unicast);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void sendSysMsg(Msg msg,int osType) {
		PushClient client = new PushClient();
		if (0 == osType) {// Android
			sendAndroidBroadcast(msg, client);
		} else if (1 == osType) {// Ios
			sendIOSBroadcast(msg, client);
		} else {// IOS,Android都发
			sendIOSBroadcast(msg, client);
			sendAndroidBroadcast(msg, client);
		}
	}

	/**
	 * IOS广播推送
	 * 
	 * @param msgNotice
	 * @param client
	 */
	private void sendIOSBroadcast(Msg msg, PushClient client) {
		IOSBroadcast iOSBroadcast = null;
		Map<String, Object> customizedMap = null;
		Iterator appKeyItr = UMengConstants.IosChannel_AppKey_Secret_Map.entrySet().iterator();
		while (appKeyItr.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> propsEntry = (Entry<String, String>) appKeyItr.next();
			try {
				iOSBroadcast = new IOSBroadcast(propsEntry.getValue().split(",")[0],
						(propsEntry.getValue().split(",")[1]));
				fillIOSNotification(msg, iOSBroadcast, customizedMap);
				client.send(iOSBroadcast);
			} catch (Exception e) {

			}
		}

	}

	/**
	 * 填充IOSNotification中单播广播共用内容
	 * 
	 * @param msgText
	 * @param iOSNotification
	 * @param customizedMap
	 */
	private void fillIOSNotification(Msg msg, IOSNotification iOSNotification,
			Map<String, Object> customizedMap) {
		try {
			Map<String, String> apsMap=new HashMap<String, String>();
			if (StringUtil.isNullOrEmpty(msg.getPushTitle())) {
				iOSNotification.setAlert(msg.getPushMsg());
			}else{
				Map<String, Object> alertMap=new HashMap<String, Object>();
				apsMap.put("title", msg.getPushTitle());
				apsMap.put("body", msg.getPushMsg());
				iOSNotification.setAlert(apsMap);
			}
			iOSNotification.setSound("suanguo_apple.caf");
			if (customizedMap != null && !customizedMap.isEmpty()) {
				for (String key : customizedMap.keySet()) {
					iOSNotification.setCustomizedField(key, customizedMap.get(key).toString());
				}
			}
			iOSNotification.setCustomizedField("innerMsg",JSON.toJSONString(msg));
			if (!AppUtils.isTestEnv()) {
				iOSNotification.setProductionMode();
			} else {
				iOSNotification.setTestMode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Android广播推送
	 * 
	 * @param msgNotice
	 * @param client
	 */
	private void sendAndroidBroadcast(Msg msg, PushClient client) {
		Map<String, Object> msgBody = new HashMap<String, Object>();
		msgBody.put("_lctype", msg.getType());
		Map<String, Object> msgBodyContent = new HashMap<String, Object>();
		msgBodyContent.put("type", msg.getType());
		Map<String, Object> msgMap =null;
		msgBodyContent.put("body", msg);
		String msgBodyString = JSON.toJSONString(msgBodyContent);
		msgBody.put("_lctext", msgBodyString);
		String msgString = JSON.toJSONString(msgBody);
		AndroidBroadcast androidBroadcast = null;
		Iterator appKeyItr = UMengConstants.AndroidChannel_AppKey_Secret_Map.entrySet().iterator();
		while (appKeyItr.hasNext()) {
			@SuppressWarnings("unchecked")
			Entry<String, String> propsEntry = (Entry<String, String>) appKeyItr.next();
			try {
				androidBroadcast = new AndroidBroadcast(propsEntry.getValue().split(",")[0],
						(propsEntry.getValue().split(",")[2]));
				androidBroadcast.setDisplayType(DisplayType.MESSAGE);
				androidBroadcast.setCustomField(msgString);
				if (!AppUtils.isTestEnv()) {
					androidBroadcast.setProductionMode();
				} else {
					androidBroadcast.setTestMode();
				}
				client.send(androidBroadcast);
			} catch (Exception e) {

			}
		}

	}

}
