package com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain.ZengjingApplyMachineRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain.ZengjingDollBusDTO;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain.ZengjingDollResult;
import com.imlianai.zjdoll.app.modules.core.push.vo.ApplyRespVO;
import com.imlianai.zjdoll.app.modules.core.push.vo.OperateRespVO;
import com.imlianai.zjdoll.app.modules.core.push.vo.PushCoinBusQueryRespVO;
import com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili.SaveRet;

public class ZengjingUtils {


	static String appid  ;
	static String appkey ;

	//测试环境
	//static String appid = "030879";
	//static String appkey = "jDMGGGeTMdzyWLeI";

	private static String hostUrl = "http://www.realgamecloud.com";
	private static String hostUrl_t = "http://119.29.208.205:6638/DollPubAppServer";

	static{
		if(AppUtils.isTestEnv()){
			//测试环境 很帅很酷很屌很牛X
			appid = "030879";
			appkey = "jDMGGGeTMdzyWLeI";
		}else{
			//正式环境 很帅很酷很屌很牛X
			appid = "370936";
			appkey = "K9ZLwVj172pXYzX4";
		}
	}



	protected static final BaseLogger logger = BaseLogger
			.getLogger(DollUtil.class);
	protected static final HashSet<String> ADDITION_KEYS = new HashSet<String>(
			Arrays.asList(new String[] { "sign", "app", "act" }));



	private static String getHost(){
		if(AppUtils.isTestEnv()){
			return hostUrl_t;
		}else{
			return hostUrl;
		}
		//return hostUrl_t;
	}
	/**
	 * 获取机器列表
	 * 
	 * @return
	 */
	public static List<ZengjingDollBusDTO> getList() {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		String sign = getSign(paraMap);
		paraMap.put("sign", sign);
		String url = getHost() + "/api/wawa/list";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		String resString = he.getHtml();
		logger.info("getList url:" + url + " resString:" + resString);
		JSONObject resp = JSON.parseObject(resString);
		if (resp.getBooleanValue("state")) {
			try {
				List<ZengjingDollBusDTO> retvalList = JSON
						.parseArray(resp.getString("dollBusList"),
								ZengjingDollBusDTO.class);
				return retvalList;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return new ArrayList<ZengjingDollBusDTO>();
	}

	public static ZengjingApplyMachineRespVO applyMachine(long uid, String busId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("busId", busId);
		paraMap.put("uid", uid);
		String sign = getSign(paraMap);
		paraMap.put("sign", sign);
		String url = getHost() + "/api/wawa/apply";
		logger.info("controlMachine url:" + url + " paraMap:" + JSON.toJSONString(paraMap));
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		String resString = he.getHtml();
		logger.info("applyMachine url:" + url + " resString:" + resString);
		ZengjingApplyMachineRespVO resp = JSON.parseObject(resString,ZengjingApplyMachineRespVO.class);
		return resp;
	}

	/**
	 * 设备操作
	 * @param uid
	 * @param busId
	 * @param optId
	 * @param action 【1】向前移动，【2】向后移动，【3】向左移动，【4】向右移动，【6】下抓，【7】停止移动
	 */
	public static void controlMachine(long uid, String busId,long optId,
			int action) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("busId", busId);
		paraMap.put("optId", optId);
		paraMap.put("type", action);
		paraMap.put("uid", uid);
		String sign = getSign(paraMap);
		paraMap.put("sign", sign);
		String url = getHost() + "/api/wawa/control";
		logger.info("controlMachine url:" + url + " paraMap:" + JSON.toJSONString(paraMap));
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		String resString = he.getHtml();
		logger.info("controlMachine url:" + url + " resString:" + resString);
	}

	/**
	 * 控制下爪
	 * @param uid
	 * @param busId
	 * @param optId
	 * @param type 【0】默认爪，【1】弱爪，【2】强爪
	 */
	public static void controlClaw(long uid, String busId,long optId, int type) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("busId", busId);
		paraMap.put("optId", optId);
		paraMap.put("type", type);
		paraMap.put("uid", uid);
		String sign = getSign(paraMap);
		paraMap.put("sign", sign);
		String url = getHost() + "/api/wawa/downClaw";
		logger.info("controlClaw url:" + url + " paraMap:" + JSON.toJSONString(paraMap));
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		String resString = he.getHtml();
		logger.info("controlClaw url:" + url + " resString:" + resString);
	}
	
	public static ZengjingDollResult getDollResult(long uid, int logId) {
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("uid", uid);
		paraMap.put("optId", logId);
		String sign = getSign(paraMap);
		paraMap.put("sign", sign);
		String url = getHost() + "/api/wawa/queryResult";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		String resString = he.getHtml();
		logger.info("applyMachine url:" + url + " resString:" + resString);
		ZengjingDollResult resp = JSON.parseObject(resString, ZengjingDollResult.class);
		return resp;
	}

	private static String getSign(Map<String, Object> paraMap) {
		String signText = DollMachSignUtil.createLinkString(DollMachSignUtil
				.paraFilter(paraMap));
		String firstSign = MD5_HexUtil.md5Hex(signText);
		String signRes = MD5_HexUtil.md5Hex(firstSign + appkey);
		return signRes;
	}
//{"deviceId":"f52700b20a3037","gameTime":30,"msg":"上机成功","optId":5059,"result":0,"state":true,"success":true}


	/**
	 * 上机
	 * @param uid
	 * @param busId
	 * @param customerId
	 * @return
	 */
	public static ApplyRespVO apply(long uid, int busId, int customerId) {
		ApplyRespVO vo = new ApplyRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("uid", uid);
		paraMap.put("busId", busId);
		paraMap.put("customerId", customerId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/apply";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			//logger.info("applyMachine url:" + url + " resString:" + resString);
			vo = JSONObject.parseObject(resString,ApplyRespVO.class);
			return vo;
		}
		return vo;
	}


	/**
	 * 投币
	 * @param uid
	 * @param busId
	 * @param customerId
	 * @return
	 */
	public static OperateRespVO push(long uid, int busId, int customerId) {
		OperateRespVO vo = new OperateRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("uid", uid);
		paraMap.put("busId", busId);
		paraMap.put("customerId", customerId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/push";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			vo = JSONObject.parseObject(resString,OperateRespVO.class);
			return vo;
		}
		return vo;
	}

	/**
	 * 摇摆
	 * @param uid
	 * @param busId
	 * @return
	 */
	public static BaseRespVO operate(long uid, int busId) {
		BaseRespVO vo = new BaseRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("uid", uid);
		paraMap.put("busId", busId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/operate";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			logger.info("applyMachine url:" + url + " resString:" + resString);
			vo = JSONObject.parseObject(resString,BaseRespVO.class);
			return vo;
		}
		return vo;
	}

	/**
	 * 查询推币机流水
	 * @param optId
	 * @param customerId
	 * @return
	 */
	public static PushCoinBusQueryRespVO query(long optId, int customerId) {
		PushCoinBusQueryRespVO vo = new PushCoinBusQueryRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("optId", optId);
		paraMap.put("customerId", customerId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/query";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			vo = JSONObject.parseObject(resString,PushCoinBusQueryRespVO.class);
			return vo;
		}
		return vo;
	}

	/**
	 * 主动下机
	 * @param uid
	 * @param busId
	 * @param customerId
	 * @return
	 */
	public static BaseRespVO finish(long uid, int busId, int customerId) {
		BaseRespVO vo = new BaseRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("uid", uid);
		paraMap.put("busId", busId);
		paraMap.put("customerId", customerId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/finish";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			logger.info("applyMachine url:" + url + " resString:" + resString);
			vo = JSONObject.parseObject(resString,BaseRespVO.class);
			return vo;
		}
		return vo;
	}


	/**
	 * 获取推币机状态
	 * @param busId
	 * @return
	 */
	public static BaseRespVO getStatus(int busId,int customerId){
		BaseRespVO vo = new BaseRespVO();
		String tsString = System.currentTimeMillis() + "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appId", appid);
		paraMap.put("ts", tsString);
		paraMap.put("busId", busId);
		paraMap.put("customerId", customerId);
		paraMap.put("sign", getSign(paraMap));
		String url = getHost() + "/api/pushCoin/getStatus";
		HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
		if (he != null){
			String resString = he.getHtml();
			logger.info("applyMachine url:" + url + " resString:" + resString);
			vo = JSONObject.parseObject(resString,BaseRespVO.class);
			return vo;
		}
		return vo;
	}

	/**
	 * 获取观看录像
	 * @param start
	 * @param end
	 * @param streamUrl
	 * @return
	 */
	public static SaveRet getVideo(long start,long end,String streamUrl){
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("start", start);
		postData.put("end", end);
		postData.put("streamUrl", streamUrl);
		HttpEntity httpEntity = HttpUtil.Post("http://www.realgamecloud.com/api/wawa/video", JSON.toJSONString(postData));
		if (httpEntity != null){
			return JSON.parseObject(httpEntity.getHtml(),SaveRet.class);
		}
		return new SaveRet();
	}


	public static void main(String[] args) {

		int busId=212;
		long uid = 373546;
		int customerId = 84;

		//System.out.println(apply(uid,busId,customerId));
		//System.out.println(push(uid,busId,customerId));
		//System.out.println(operate(uid,busId));
		//System.out.println(query(216,customerId));
		//query(205,customerId);
		//System.out.println(finish(uid,busId,customerId));
	}

	
	
}
