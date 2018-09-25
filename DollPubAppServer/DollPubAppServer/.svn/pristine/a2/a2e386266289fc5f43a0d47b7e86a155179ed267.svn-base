package com.imlianai.dollpub.app.modules.core.doll.utils;

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
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QIyiguoApplyResp;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoDeviceSetting;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoDollResult;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;

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
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		System.out.println(uri);
		String url = hostUrl + "/api/index.php?" + uri;
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
		String url = hostUrl + "/api/index.php?" + uri;
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
		String url = hostUrl + "/api/index.php?" + uri;
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
		String url = hostUrl + "/api/index.php?" + uri;
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
		String url = hostUrl + "api/index.php?" + uri;
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
	 * 创建订单
	 * 
	 * @param deviceId
	 * @return
	 */
	public static Map<String, Object> createOrder(long deviceId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("app", "buyer_order");
		reqMap.put("act", "create_order");
		reqMap.put("platform", platform);
		reqMap.put("ts", tsString);
		String sign = getSign(reqMap);// MD5_HexUtil.md5Hex(signParam + secret);
		reqMap.put("sign", sign);
		String uri = getUri(reqMap);
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;

		HttpEntity he = HttpUtil.Get(url);
		String resString = he.getHtml();
		logger.info("getDollResult url:" + url + " resString:" + resString);
		Map<String, Object> resMap = JSON.parseObject(resString, Map.class);
		return resMap;
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
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;
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
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;
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
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;
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
		String url = "http://doll.artqiyi.com/api/index.php?" + uri;
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

	public static void main(String[] args) {
		// JSONArray res=DollUtil.getDeviceList();
		// System.out.println(res);
		String device = "8oWdEjqi5ZHjjYvKCrh4WB";
		long uid = 10010;
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
			"%s 只花了%s次就抓到了，神来之手，就问还有谁？！", "%s 只花了%s次就抓到了，给你投来了王之蔑视",
			"%s 只花了%s次就抓到了，还在填坑的亲你们还好吗？" };

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
			int dayCount) {
		if (dayCount <= 10) {
			int ran = new Random().nextInt(ONLINEMSG_10.length);
			try {
				return String
						.format(ONLINEMSG_10[ran], userName, dayCount + "");
			} catch (Exception e) {
			}
			return String.format(ONLINEMSG_10[0], userName, dayCount + "");
		} else {
			int ran = new Random().nextInt(ONLINEMSG_10_MORE.length);
			try {
				return String
						.format(ONLINEMSG_10_MORE[ran], userName, dollName);
			} catch (Exception e) {
			}
			return String.format(ONLINEMSG_10_MORE[0], userName, dollName);
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
