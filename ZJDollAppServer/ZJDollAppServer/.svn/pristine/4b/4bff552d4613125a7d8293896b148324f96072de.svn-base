package com.imlianai.zjdoll.app.modules.core.doll.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QIyiguoApplyResp;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoDeviceSetting;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoDollResult;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoGoods;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;

/**
 * 奇异果工具
 * 
 * @author tensloveq
 * 
 */
public class DollUtil {

	static String platform = "suanguo";
	static String secret = "a96702cd3b5b57f32d4b28f1c40d5e93";
	protected static final BaseLogger logger = BaseLogger
			.getLogger(DollUtil.class);
	protected static final HashSet<String> ADDITION_KEYS = new HashSet<String>(
			Arrays.asList(new String[] { "sign", "app", "act" }));

	private static String hostUrl = "http://doll.artqiyi.com";
	private static String hostUrl_t = "http://testdoll.artqiyi.com";

	// $data为除sign,app,act之外的所有请求参数

	private static String getHost(){
		return hostUrl;
		//return AppUtils.isTestEnv() ? hostUrl_t:hostUrl;
	}
	/**
	 * 获取设备列表
	 * 
	 * @return
	 */
	public static List<QiyiguoMachine> getDeviceList() {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "doll_list");
		reqMap.put("platform", platform);
		reqMap.put("ts", tsString);
		reqMap.put("per_page", 200);
		
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		System.out.println(uri);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDeviceList url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("done")) {
			try {
				JSONObject retval = resp.getJSONObject("retval");
				List<QiyiguoMachine> retvalList = JSON.parseObject(
						retval.getString("list"),
						new TypeReference<List<QiyiguoMachine>>() {
						});
				return retvalList;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return new ArrayList<QiyiguoMachine>();
	}

	/**
	 * 上机
	 * 
	 * @param uid
	 * @param device
	 * @return
	 */
	public static QIyiguoApplyResp applyMachine(long uid, String device) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "assign");
		reqMap.put("device_id", device);
		reqMap.put("user_id", uid);
		reqMap.put("platform", platform);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("applyMachine url:" + url + " resString:" + resString);
		QIyiguoApplyResp resMap = JSON.parseObject(resString,
				QIyiguoApplyResp.class);
		return resMap;
	}
	/**
	 * 上机
	 * 
	 * @param uid
	 * @param device
	 * @return
	 */
	public static QIyiguoApplyResp applyMachine(long uid, String device,int power_grab,int power_up) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "assign");
		reqMap.put("device_id", device);
		reqMap.put("user_id", uid);
		reqMap.put("platform", platform);
		String configString="{\"power_grab\":"+power_grab+",\"power_up\":"+power_up+"}";
		reqMap.put("config", configString);
		
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("applyMachine url:" + url + " resString:" + resString);
		QIyiguoApplyResp resMap = JSON.parseObject(resString,
				QIyiguoApplyResp.class);
		return resMap;
	}
	/**
	 * 设备操作
	 * 
	 * @param uid
	 * @param device
	 * @param action
	 * @return
	 */
	public static Map<String, Object> controlMachine(long uid, String device,
			int action) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "operate");
		reqMap.put("device_id", device);
		reqMap.put("user_id", uid);
		reqMap.put("action", action);
		reqMap.put("platform", platform);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("applyMachine url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
	}

	/**
	 * 获取捉取结果
	 * 
	 * @param logId
	 * @return
	 */
	public static QiyiguoDollResult getDollResult(long logId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "operate_result");
		reqMap.put("platform", platform);
		reqMap.put("log_id", logId);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDollResult url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("done")) {
			try {
				JSONObject retval = resp.getJSONObject("retval");
				QiyiguoDollResult res = retval
						.toJavaObject(QiyiguoDollResult.class);
				return res;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return null;
	}

	/**
	 * 获取设备状态
	 * 
	 * @param deviceId
	 * @return
	 */
	public static QiyiguoMachine getDeviceStatus(String deviceId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "get_device_status");
		reqMap.put("platform", platform);
		reqMap.put("device_id", deviceId);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost() + "/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();

		logger.info("getDeviceStatus url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("done")) {
			try {
				JSONObject retval = resp.getJSONObject("retval");
				QiyiguoMachine qiyiguoMachine = retval
						.toJavaObject(QiyiguoMachine.class);
				return qiyiguoMachine;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return null;
	}

	/**
	 * 设置中奖概率
	 * 
	 * @param deviceId
	 * @return
	 */
	public static Map<String, Object> setProbability(String deviceId,
			int probability) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "set_winning_probability");
		reqMap.put("platform", platform);
		reqMap.put("device_id", deviceId);
		reqMap.put("winning_probability", probability);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost()+"/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDollResult url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
	}

	/**
	 * 设置抓力模式
	 * 
	 * @param deviceId
	 * @param mode
	 * @return
	 */
	public static Map<String, Object> setMode(String deviceId, int mode) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "set_holding_mode");
		reqMap.put("platform", platform);
		reqMap.put("device_id", deviceId);
		reqMap.put("holding_mode", mode);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost()+"/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDollResult url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
	}

	/**
	 * 设置机器游戏时间
	 * 
	 * @param deviceId
	 * @param second
	 * @return
	 */
	public static Map<String, Object> setPlaytime(String deviceId, int second) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "set_playtime");
		reqMap.put("platform", platform);
		reqMap.put("device_id", deviceId);
		reqMap.put("playtime", second);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost()+"/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDollResult url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
	}

	/**
	 * 设置机器游戏时间
	 * 
	 * @param deviceId
	 * @param second
	 * @return
	 */
	public static QiyiguoDeviceSetting getDeviceSetting(String deviceId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "get_device_setting");
		reqMap.put("platform", platform);
		reqMap.put("device_ids", deviceId);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost()+"/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDeviceSetting url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("done")) {
			try {

				try {
					List<QiyiguoDeviceSetting> retvalList = JSON.parseObject(
							resp.getString("retval"),
							new TypeReference<List<QiyiguoDeviceSetting>>() {
							});
					if (!StringUtil.isNullOrEmpty(retvalList)) {
						return retvalList.get(0);
					}
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return null;
	}

	/**
	 * 设置机器游戏时间
	 * 
	 * @param deviceId
	 * @param second
	 * @return
	 */
	public static boolean setHoldingForce(String deviceId, int strong, int week) {
		// /api/index.php?app=doll&act=set_holding_force
		// &platform=qyguo&device_id=o8XkMLQkSDVjEqNKeyw7aG&strong_force
		// =32&weak_force=10&sign=9745e150580e77120abde8ea8d7b6ffc&ts=1509695453
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "doll");
		reqMap.put("act", "set_holding_force");
		reqMap.put("platform", platform);
		reqMap.put("device_id", deviceId);
		reqMap.put("strong_force", strong);
		reqMap.put("weak_force", week);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = getHost()+"/api/index.php?" + uri;
		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDeviceSetting url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("done")) {
			return true;
		}
		return false;
	}

	//

	/**
	 * 创建订单
	 * 
	 * @param deviceId
	 * @return
	 */
	public static Map<String, Object> createOrder(long uid, String name,
			String address, long mobile, String consignee,
			List<QiyiguoGoods> goodsList) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "buyer_order");
		reqMap.put("act", "create_order");
		reqMap.put("platform", platform);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("platform", platform);
		postMap.put("user_id", uid);
		postMap.put("username", name);
		postMap.put("goods_list", JSON.toJSONString(goodsList));
		postMap.put("address", address);
		postMap.put("mobile", mobile);
		postMap.put("consignee", consignee);
		postMap.put("ts", tsString);
		sign = getSign(postMap);
		postMap.put("sign", sign);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type",
				"multipart/form-data; boundary=----footfoodapplicationrequestnetwork");
		HttpEntity he = HttpUtil.Post(url, getFormData(postMap), headers);
		String resString = he.getHtml();
		logger.info("createOrder url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
	}

	public static boolean checkDone(String resp) {
		if (StringUtil.isNullOrEmpty(resp)) {
			return false;
		}
		JSONObject respJSON = JSON.parseObject(resp);
		if (respJSON.getBooleanValue("done")) {
			return true;
		}
		return false;
	}

	public static long getShippingOrderId(String resp) {
		if (!StringUtil.isNullOrEmpty(resp)) {
			JSONObject respJSON = JSON.parseObject(resp);
			if (respJSON.getBooleanValue("done")) {
				JSONObject retval = respJSON.getJSONObject("retval");
				long orderId = retval.getLong("order_id");
				return orderId;
			}
		}
		return 0;
	}

	public static String getRespMsg(String resp) {
		if (!StringUtil.isNullOrEmpty(resp)) {
			JSONObject respJSON = JSON.parseObject(resp);
			return respJSON.getString("msg");
		}
		return null;
	}

	public static void main(String[] args) {
		String url = "1513826852&order_id=9198&shipping_no=537770226592&shipping_com=zhongtong&shipping_memo=&shipping_time=1513826852&mode=EXPRESS&status=30&shipping_name=%E4%B8%AD%E9%80%9A%E5%BF%AB%E9%80%92&is_edit=0&sign=df2114a8b27e469a187b3487f0605296";
		String hostString = "http://www.mengquww.com/api/qygshipping/doCommand/?";
		System.out.println(HttpUtil.Get(hostString + url).getHtml());
		// System.out.println(HttpUtil.Get("http://t.xiehou360.com/LiveWebServer/call.do?cmd=mobile.initRobot&code=decode&shipping_name=%E4%B8%AD%E9%80%9A%E5%BF%AB%E9%80%92"));

		/*
		 * try { System.out.println(URLDecoder.decode(
		 * "%E4%B8%AD%E9%80%9A%E5%BF%AB%E9%80%92","UTF-8")); } catch
		 * (UnsupportedEncodingException e) { e.printStackTrace(); }
		 */
	}

	private static String getFormData(Map<String, Object> postMap) {
		StringBuffer buffer = new StringBuffer();
		if (!StringUtil.isNullOrEmpty(postMap)) {
			for (String key : postMap.keySet()) {
				buffer.append("------footfoodapplicationrequestnetwork\r\n");
				buffer.append("Content-Disposition: form-data; name=\"");
				buffer.append(key);
				buffer.append("\"\r\n\r\n");
				buffer.append(postMap.get(key));
				buffer.append("\r\n");
			}
			buffer.append("------footfoodapplicationrequestnetwork--\r\n");
		}
		return buffer.toString();
	}

	/**
	 * 签名
	 * 
	 * @param reqMap
	 * @return
	 */
	public static String getSign(Map<String, Object> reqMap) {
		String signParam = "";
		if (reqMap == null) {
			return null;
		}
		StringBuffer content = new StringBuffer();
		List keys = new ArrayList(reqMap.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
			if (ADDITION_KEYS.contains(key)) {
				continue;
			}
			signParam += key + reqMap.get(key).toString();
		}
		String firstSign = MD5_HexUtil.md5Hex(signParam);
		String sign = MD5_HexUtil.md5Hex(firstSign + secret);
		return sign;
	}

	/**
	 * 获取请求参数
	 * 
	 * @param params
	 * @return
	 */
	public static String getUri(Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		StringBuffer content = new StringBuffer();
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
			String value = params.get(key).toString();
			content.append(((i == 0) ? "" : "&") + key + "=" + value);
		}
		return content.toString();
	}

	private static final String[] ONLINEMSG_10 = {
			"%s 只花了%s次就抓到了 %s，神来之手，就问还有谁？！", "%s 只花了%s次就抓到了 %s，给你投来了王之蔑视",
			"%s 只花了%s次就抓到了 %s，还在填坑的亲你们还好吗？" };

	private static final String[] ONLINEMSG_4 = {
			"%s 只花了%s次就在%s号机抓到了 %s，简直是黄金圣手，就问还有谁？！",
			"%s只花了%s次就在%s号机抓到了 %s，给你投来了王之蔑视",
			"天啦噜，%s 只花了%s次就在%s号机抓到了 %s，心疼还在填坑的亲",
			"明明可以靠颜值却偏要靠才华，%s 只花了%s次就在%s号机抓到了 %s",
			"实力吊炸天，%s 只花了%s次就在%s号机抓到了 %s" };

	private static final String[] ONLINEMSG_4_MORE = {
			"%s 在%s号机抓到了 %s！实力派就是不一样~~", "吼吼吼，%s 在%s号机抓到了 %s，为TA疯狂打CALL~~",
			"%s 在%s号机抓到了 %s，666666", "%s 在%s号机抓到了 %s，不服来战！",
			"%s 在%s号机抓到了 %s，大吉大利，今晚吃鸡~~", "%s 在%s号机抓到了 %s，感觉自己萌萌哒~~" };

	private static final String[] ONLINEMSG_10_MORE = {
			"%s 抓到 %s 啦，实力派就是不一样~~", "%s 抓到 %s 啦，为TA疯狂打CALL~~",
			"%s 抓到 %s 啦，666666", "%s 抓到 %s 啦，不服来战", "%s 抓到 %s 啦，大吉大利，今晚吃鸡~~",
			"%s 抓到 %s 啦，简直就是黄金圣手！", "%s 抓到 %s 啦，感觉自己萌萌哒~~",
			"%s 抓到 %s 啦，感谢前面填坑的亲~~" };

	/**
	 * 获取全站随机成功文案
	 * 
	 * @param userName
	 * @param dollName
	 * @param dayCount
	 * @return
	 */
	public static String getAppSuccessMsg(String userName, String dollName,
			int busId, int dayCount) {
		if (dayCount <= 4) {
			int ran = new Random().nextInt(ONLINEMSG_4.length);
			try {
				return String.format(ONLINEMSG_4[ran], userName, dayCount + "",
						busId + "", dollName);
			} catch (Exception e) {
			}
			return String.format(ONLINEMSG_4[0], userName, dayCount + "",
					dollName);
		} else {
			int ran = new Random().nextInt(ONLINEMSG_4_MORE.length);
			try {
				return String.format(ONLINEMSG_4_MORE[ran], userName, busId
						+ "", dollName);
			} catch (Exception e) {
			}
			return String.format(ONLINEMSG_4_MORE[0], userName, busId + "",
					dollName);
		}
	}

	private static final String[] ONLINEMSG_FAIL = { "%s 错过抓中的机会，就像当年错过了初恋",
			"%s 没抓到，被大家diss了~~~", "%s 没抓到，求安慰~~~", "%s 没抓到，在厕所哭晕了",
			"%s 没抓到，帮大家填了一次坑", "%s 没抓到，哈哈哈哈哈哈哈哈" };

	public static String getAppFailMsg(String userName) {
		int ran = new Random().nextInt(ONLINEMSG_FAIL.length);
		try {
			return String.format(ONLINEMSG_FAIL[ran], userName);
		} catch (Exception e) {
		}
		return String.format(ONLINEMSG_FAIL[0], userName);
	}

}
